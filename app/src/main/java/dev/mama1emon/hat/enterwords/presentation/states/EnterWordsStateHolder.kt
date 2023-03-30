package dev.mama1emon.hat.enterwords.presentation.states

import dev.mama1emon.hat.enterwords.presentation.models.EnterWordFieldModel
import dev.mama1emon.hat.enterwords.presentation.models.Word

/**
 * @author Andrew Khokhlov on 22/03/2023
 */
sealed interface EnterWordsStateHolder {

    val playerName: String
    val progress: Float

    data class Empty(
        override val playerName: String,
        override val fieldModel: EnterWordFieldModel
    ) : EnterWordsStateHolder, EnterWordAvailability {
        override val progress: Float = 0.009f
    }

    data class NotYet(
        override val playerName: String,
        override val progress: Float,
        override val fieldModel: EnterWordFieldModel,
        override val wordlist: List<Word>,
        override val onRemoveWordButtonClick: (Int) -> Unit,
    ) : EnterWordsStateHolder, EnterWordAvailability, RemoveWordsAvailability

    data class Ready(
        override val playerName: String,
        override val wordlist: List<Word>,
        override val onRemoveWordButtonClick: (Int) -> Unit,
        val onReadyButtonClick: () -> Unit
    ) : EnterWordsStateHolder, RemoveWordsAvailability {
        override val progress: Float = 1.0f
    }
}