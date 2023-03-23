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
        when (gameManager.currentStep) {
            is GameStep.Greeting -> {
                navController.navigate(Screens.Greeting.route) {
                    popUpTo(Screens.Greeting.route) { inclusive = true }
                }
            }
            is GameStep.StartTeamsPreparingStep -> {
                navController.navigate(Screens.AddTeam.route)
            }
            is GameStep.FinishTeamsPreparingStep -> {
                navController.navigate(Screens.Announcement.route)
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