package dev.mama1emon.hat.enterwords.presentation.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mama1emon.hat.domain.models.Player
import dev.mama1emon.hat.features.enterwords.presentation.models.EnterWordFieldModel
import dev.mama1emon.hat.features.enterwords.presentation.models.Word
import dev.mama1emon.hat.features.enterwords.presentation.states.EnterWordAvailability
import dev.mama1emon.hat.features.enterwords.presentation.states.EnterWordsStateHolder
import dev.mama1emon.hat.features.enterwords.presentation.states.RemoveWordsAvailability
import dev.mama1emon.hat.game.GameManager
import dev.mama1emon.hat.game.GameRules
import dev.mama1emon.hat.navigation.Screens
import java.util.*
import javax.inject.Inject

/**
 * @author Andrew Khokhlov on 22/03/2023
 */
@HiltViewModel
class EnterWordsViewModel @Inject constructor(
    private val gameManager: GameManager,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val uiStateEditor = UiStateEditor()
    private val inputFieldEditor = InputFieldEditor()

    var uiState: EnterWordsStateHolder by mutableStateOf(uiStateEditor.getInitialUiState())
        private set

    private inner class UiStateEditor {

        fun getInitialUiState(): EnterWordsStateHolder {
            val player = Gson().fromJson(
                requireNotNull(savedStateHandle[Screens.PLAYER_KEY]) as String,
                Player::class.java
            )
            return if (player.wordlist.isEmpty()) {
                EnterWordsStateHolder.Empty(
                    playerName = player.name,
                    fieldModel = EnterWordFieldModel(
                        value = "",
                        hasError = false,
                        addWordButtonAvailability = false,
                        onValueChanged = inputFieldEditor::changeWord,
                        onDoneClick = ::addWord
                    )
                )
            } else if (player.wordlist.size in 0 until gameManager.rules.wordlistSizePerPerson) {
                EnterWordsStateHolder.NotYet(
                    playerName = player.name,
                    fieldModel = EnterWordFieldModel(
                        value = "",
                        hasError = false,
                        addWordButtonAvailability = false,
                        onValueChanged = inputFieldEditor::changeWord,
                        onDoneClick = ::addWord
                    ),
                    wordlist = player.wordlist.map(::Word),
                    onRemoveWordButtonClick = ::removeWord,
                    progress = calculateProgress(player.wordlist.size)
                )
            } else {
                EnterWordsStateHolder.Ready(
                    playerName = player.name,
                    wordlist = player.wordlist.map(::Word),
                    onRemoveWordButtonClick = ::removeWord,
                    onReadyButtonClick = { gameManager.finishPlayerPreparing(player.wordlist) }
                )
            }
        }

        private fun addWord() {
            (uiState as? EnterWordAvailability)?.let { state ->
                uiState = when (state) {
                    is EnterWordsStateHolder.Empty -> {
                        EnterWordsStateHolder.NotYet(
                            playerName = state.playerName,
                            fieldModel = state.fieldModel.copy(
                                value = "",
                                hasError = false,
                                addWordButtonAvailability = false
                            ),
                            wordlist = listOf(Word(state.fieldModel.value)),
                            onRemoveWordButtonClick = ::removeWord,
                            progress = calculateProgress(enteredWordsAmount = 1)
                        )
                    }
                    is EnterWordsStateHolder.NotYet -> {
                        if (state.wordlist.size in 1 until gameManager.rules.wordlistSizePerPerson - 1) {
                            EnterWordsStateHolder.NotYet(
                                playerName = state.playerName,
                                fieldModel = state.fieldModel.copy(
                                    value = "",
                                    hasError = false,
                                    addWordButtonAvailability = false
                                ),
                                wordlist = listOf(
                                    Word(state.fieldModel.value),
                                    *state.wordlist.toTypedArray()
                                ),
                                onRemoveWordButtonClick = ::removeWord,
                                progress = calculateProgress(
                                    enteredWordsAmount = state.wordlist.size + 1
                                )
                            )
                        } else {
                            val wordlist = listOf(
                                Word(state.fieldModel.value),
                                *state.wordlist.toTypedArray()
                            )
                            EnterWordsStateHolder.Ready(
                                playerName = state.playerName,
                                wordlist = wordlist,
                                onRemoveWordButtonClick = state.onRemoveWordButtonClick,
                                onReadyButtonClick = {
                                    gameManager.finishPlayerPreparing(
                                        wordlist = wordlist.map(Word::value)
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }

        private fun removeWord(removableIndex: Int) {
            (uiState as? RemoveWordsAvailability)?.let { state ->
                uiState = when (state) {
                    is EnterWordsStateHolder.NotYet -> {
                        if (state.wordlist.size == 1) {
                            EnterWordsStateHolder.Empty(
                                playerName = state.playerName,
                                fieldModel = state.fieldModel,
                            )
                        } else {
                            EnterWordsStateHolder.NotYet(
                                playerName = state.playerName,
                                fieldModel = state.fieldModel,
                                wordlist = state.wordlist.filterIndexed { index, _ ->
                                    index != removableIndex
                                },
                                onRemoveWordButtonClick = state.onRemoveWordButtonClick,
                                progress = calculateProgress(
                                    enteredWordsAmount = state.wordlist.size - 1
                                )
                            )
                        }
                    }
                    is EnterWordsStateHolder.Ready -> {
                        EnterWordsStateHolder.NotYet(
                            playerName = state.playerName,
                            fieldModel = EnterWordFieldModel(
                                value = "",
                                hasError = false,
                                addWordButtonAvailability = false,
                                onValueChanged = inputFieldEditor::changeWord,
                                onDoneClick = ::addWord
                            ),
                            wordlist = state.wordlist.filterIndexed { index, _ ->
                                index != removableIndex
                            },
                            onRemoveWordButtonClick = state.onRemoveWordButtonClick,
                            progress = calculateProgress(
                                enteredWordsAmount = state.wordlist.size - 1
                            )
                        )
                    }
                }
            }
        }
    }

    private fun calculateProgress(enteredWordsAmount: Int): Float {
        return String
            .format(
                format = "%.2f",
                enteredWordsAmount.toFloat().div(gameManager.rules.wordlistSizePerPerson)
            )
            .toFloat()
    }

    private inner class InputFieldEditor {

        fun changeWord(newWord: String) {
            val rightFormWord = newWord.toRightForm()
            val isValidWord = isValidWord(rightFormWord)

            val isUniqueWord = (uiState as? EnterWordsStateHolder.NotYet)?.let { state ->
                rightFormWord !in state.wordlist.map(Word::value)
            } ?: true

            (uiState as? EnterWordAvailability)?.let { state ->
                uiState = state.copySealed(
                    fieldModel = state.fieldModel.copy(
                        value = rightFormWord,
                        hasError = !isValidWord || !isUniqueWord,
                        addWordButtonAvailability = isValidWord && rightFormWord.isNotBlank() &&
                            isUniqueWord
                    )
                )
            }
        }

        private fun String.toRightForm(): String {
            return this
                .lowercase()
                .replaceFirstChar { firstLetter ->
                    if (firstLetter.isLowerCase()) {
                        firstLetter.titlecase(Locale.ROOT)
                    } else {
                        firstLetter.toString()
                    }
                }
        }

        private fun isValidWord(word: String): Boolean {
            return Regex(pattern = GameRules.WORD_PATTERN).matches(input = word)
        }
    }
}