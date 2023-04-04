package dev.mama1emon.hat.features.enterwords.presentation.states

import dev.mama1emon.hat.features.enterwords.presentation.models.Word

sealed interface RemoveWordsAvailability {
    val wordlist: List<Word>
    val onRemoveWordButtonClick: (Int) -> Unit
}