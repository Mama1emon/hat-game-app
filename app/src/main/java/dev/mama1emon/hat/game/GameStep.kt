package dev.mama1emon.hat.game

import java.util.*

/**
 * @author Andrew Khokhlov on 22/03/2023
 */
sealed interface GameStep {

    object Greeting : GameStep

    object StartTeamsPreparingStep : GameStep

    data class FinishTeamsPreparingStep(
        val teamId: UUID,
        val playerId: UUID,
        val teamName: String,
        val playerName: String
    ) : GameStep

    data class StartPlayerPreparingStep(
        val teamId: UUID,
        val playerId: UUID,
        val playerName: String
    ) : GameStep

    data class FinishPlayerPreparingStep(
        val teamId: UUID,
        val playerId: UUID,
        val teamName: String,
        val playerName: String
    ) : GameStep

    object StartGame : GameStep
}