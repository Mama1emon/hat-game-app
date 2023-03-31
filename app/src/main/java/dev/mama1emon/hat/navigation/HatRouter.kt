package dev.mama1emon.hat.navigation

import android.widget.Toast
import androidx.navigation.NavHostController
import dev.mama1emon.hat.HatActivity
import dev.mama1emon.hat.R
import dev.mama1emon.hat.game.GameManager
import dev.mama1emon.hat.game.GameStep

/**
 * @author Andrew Khokhlov on 23/03/2023
 */
object HatRouter {

    private var isFirstAttemptToExit = true

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
                    route = Screens.PlayerPreparingAnnouncement.route(
                        teamName = step.teamName,
                        playerName = step.playerName
                    )
                )
            }
            is GameStep.StartPlayerPreparingStep -> {
                navController.navigate(route = Screens.EnterWords.route(step.player))
            }
            is GameStep.FinishPlayerPreparingStep -> {
                navController.navigate(
                    route = Screens.PlayerPreparingAnnouncement.route(
                        teamName = step.teamName,
                        playerName = step.playerName
                    )
                )
            }
            is GameStep.StartGameRoundStep -> {
                navController.navigate(route = Screens.GameRoundAnnouncement.route(round = step))
            }
            is GameStep.StartPlayerMoveStep -> {
                navController.navigate(
                    route = Screens.PlayerMoveAnnouncement.route(
                        teamName = step.teamName,
                        playerName = step.playerName
                    )
                )
            }
            is GameStep.PlayerMoveStep -> {
                // TODO("HAT-32")
            }
        }
        isFirstAttemptToExit = true
    }

    fun handleOnBackPressedEvent(navController: NavHostController, gameManager: GameManager) {
        when (gameManager.currentStep) {
            is GameStep.Greeting -> {
                requireNotNull(navController.context as? HatActivity).finish()
            }
            is GameStep.StartTeamsPreparingStep,
            is GameStep.FinishTeamsPreparingStep,
            is GameStep.StartPlayerPreparingStep,
            is GameStep.FinishPlayerPreparingStep -> {
                isFirstAttemptToExit = if (isFirstAttemptToExit) {
                    Toast.makeText(
                        navController.context,
                        R.string.are_you_sure_you_want_to_go_out,
                        Toast.LENGTH_LONG
                    ).show()
                    false
                } else {
                    gameManager.cancelPreparing()
                    true
                }
            }
            is GameStep.StartGameRoundStep,
            is GameStep.StartPlayerMoveStep,
            is GameStep.PlayerMoveStep -> {
                // TODO("HAT-37")
            }
        }
    }
}