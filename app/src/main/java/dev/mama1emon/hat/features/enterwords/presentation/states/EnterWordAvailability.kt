package dev.mama1emon.hat.features.enterwords.presentation.states

import dev.mama1emon.hat.features.enterwords.presentation.models.EnterWordFieldModel

sealed interface EnterWordAvailability {
    val fieldModel: EnterWordFieldModel

    fun copySealed(fieldModel: EnterWordFieldModel = this.fieldModel): EnterWordsStateHolder {
        return when (this) {
            is EnterWordsStateHolder.Empty -> copy(fieldModel = fieldModel)
            is EnterWordsStateHolder.NotYet -> copy(fieldModel = fieldModel)
        }
    }
}