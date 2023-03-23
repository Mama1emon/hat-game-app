package dev.mama1emon.hat.enterwords.presentation.states

import dev.mama1emon.hat.enterwords.presentation.models.EnterWordFieldModel
import dev.mama1emon.hat.enterwords.presentation.models.Word

/**
 * @author Andrew Khokhlov on 22/03/2023
 */
sealed interface EnterWordsStateHolder {

    val playerName: String

    sealed interface EnterWordAvailability {
        val fieldModel: EnterWordFieldModel

        fun copySealed(fieldModel: EnterWordFieldModel = this.fieldModel): EnterWordsStateHolder {
            return when (this) {
                is Empty -> copy(fieldModel = fieldModel)
                is NotYet -> copy(fieldModel = fieldModel)
            }
        }
    }

    sealed interface RemoveWordsAvailability {
        val words: List<Word>
        val onRemoveWordButtonClick: (Int) -> Unit
    }

    data class Empty(
        override val playerName: String,
        override val fieldModel: EnterWordFieldModel
    ) : EnterWordsStateHolder, EnterWordAvailability

    data class NotYet(
        override val playerName: String,
        override val fieldModel: EnterWordFieldModel,
        override val words: List<Word>,
        override val onRemoveWordButtonClick: (Int) -> Unit,
        val progress: Float,
    ) : EnterWordsStateHolder, EnterWordAvailability, RemoveWordsAvailability

    data class Ready(
        override val playerName: String,
        override val words: List<Word>,
        override val onRemoveWordButtonClick: (Int) -> Unit,
        val onReadyButtonClick: () -> Unit
    ) : EnterWordsStateHolder, RemoveWordsAvailability
}