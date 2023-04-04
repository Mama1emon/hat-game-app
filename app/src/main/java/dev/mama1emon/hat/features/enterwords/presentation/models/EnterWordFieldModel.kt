package dev.mama1emon.hat.features.enterwords.presentation.models

/**
 * @author Andrew Khokhlov on 22/03/2023
 */
data class EnterWordFieldModel(
    val value: String,
    val hasError: Boolean,
    val addWordButtonAvailability: Boolean,
    val onValueChanged: (String) -> Unit,
    val onDoneClick: () -> Unit
)
