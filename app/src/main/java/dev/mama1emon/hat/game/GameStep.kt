package dev.mama1emon.hat.game

import dev.mama1emon.hat.domain.models.Player

/**
 * @author Andrew Khokhlov on 22/03/2023
 */
sealed interface GameStep {

    object Greeting : GameStep

    object StartTeamsPreparingStep : GameStep

    data class FinishTeamsPreparingStep(val teamName: String, val playerName: String) : GameStep

    data class StartPlayerPreparingStep(val player: Player) : GameStep

    data class FinishPlayerPreparingStep(val teamName: String, val playerName: String) : GameStep

    object StartGame : GameStep
}