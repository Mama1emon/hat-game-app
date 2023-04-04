package dev.mama1emon.hat.features.game.presentation.state

/**
 * @author Andrew Khokhlov on 04/04/2023
 */
sealed interface GameStateHolder {

    data class Content(
        val currentTime: String,
        val word: String,
        val onGuessSuccess: () -> Unit,
        val onGuessFailure: () -> Unit,
    ) : GameStateHolder
}