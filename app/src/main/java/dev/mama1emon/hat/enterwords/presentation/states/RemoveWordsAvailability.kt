package dev.mama1emon.hat.enterwords.presentation.states

import dev.mama1emon.hat.enterwords.presentation.models.Word

sealed interface RemoveWordsAvailability {
    val wordlist: List<Word>
    val onRemoveWordButtonClick: (Int) -> Unit
}