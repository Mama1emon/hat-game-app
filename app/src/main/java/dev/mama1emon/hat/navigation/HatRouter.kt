package dev.mama1emon.hat.navigation

import androidx.navigation.NavHostController
import dev.mama1emon.hat.HatActivity
import dev.mama1emon.hat.game.GameManager
import dev.mama1emon.hat.game.GameStep

/**
 * @author Andrew Khokhlov on 23/03/2023
 */
object HatRouter {

    fun subscribeOnGameEvent(navController: NavHostController, gameManager: GameManager) {
        when (val step = gameManager.currentStep) {
            is GameStep.Greeting -> {
                navController.navigate(route = Screens.Greeting.value()) {
                    popUpTo(route = Screens.Greeting.value()) { inclusive = true }
                }
            }
            is GameStep.StartTeamsPreparingStep -> {
                navController.navigate(route = Screens.AddTeam.value())
            }
            is GameStep.FinishTeamsPreparingStep -> {
                navController.navigate(
                    route = Screens.PlayerAttention.route(
                        teamId = step.teamId,
                        playerId = step.playerId,
                        teamName = step.teamName,
                        playerName = step.playerName
                    )
                )
            }
            is GameStep.StartPlayerPreparingStep -> {
                //TODO()
            }
            is GameStep.FinishPlayerPreparingStep -> {
                //TODO()
            }
            is GameStep.StartGame -> {
                //TODO()
            }
        }
    }

    fun handleOnBackPressedEvent(navController: NavHostController, gameManager: GameManager) {
        when (gameManager.currentStep) {
            is GameStep.Greeting -> requireNotNull(navController.context as? HatActivity).finish()
            is GameStep.StartTeamsPreparingStep,
            is GameStep.FinishTeamsPreparingStep,
            is GameStep.StartPlayerPreparingStep,
            is GameStep.FinishPlayerPreparingStep -> gameManager.cancelPreparing()
            GameStep.StartGame -> TODO()
        }
    }
}