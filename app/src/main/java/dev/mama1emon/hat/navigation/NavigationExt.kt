package dev.mama1emon.hat.navigation

import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.google.gson.Gson
import dev.mama1emon.hat.LocalActivityViewModelStoreOwner
import dev.mama1emon.hat.LocalGameManager
import dev.mama1emon.hat.R
import dev.mama1emon.hat.addteam.presentation.ui.AddTeamsScreen
import dev.mama1emon.hat.addteam.presentation.viewmodels.AddTeamsViewModel
import dev.mama1emon.hat.announcement.move.presentation.ui.PlayerMoveAnnouncementScreen
import dev.mama1emon.hat.announcement.preparing.presentation.ui.PlayerPreparingAnnouncementScreen
import dev.mama1emon.hat.announcement.presentation.ui.AnnouncementComponent
import dev.mama1emon.hat.announcement.round.presentation.ui.GameRoundAnnouncementScreen
import dev.mama1emon.hat.ds.theme.CitrusZest
import dev.mama1emon.hat.enterwords.presentation.ui.EnterWordsScreen
import dev.mama1emon.hat.enterwords.presentation.viewmodels.EnterWordsViewModel
import dev.mama1emon.hat.game.GameStep
import dev.mama1emon.hat.greeting.presentation.ui.GreetingScreen
import dev.mama1emon.hat.navigation.Screens.*

const val HAT_GRAPH_NAME = "hat"

fun NavGraphBuilder.hatGraph() {
    navigation(route = HAT_GRAPH_NAME, startDestination = Greeting.value()) {
        composable(route = Greeting.value()) {
            GreetingScreen()
        }

        composable(route = AddTeam.value()) {
            val viewModel = hiltViewModel<AddTeamsViewModel>(
                viewModelStoreOwner = LocalActivityViewModelStoreOwner.current
            )

            AddTeamsScreen(stateHolder = viewModel.uiState)
        }

        composable(
            route = PlayerPreparingAnnouncement.value(),
            arguments = PlayerPreparingAnnouncement.arguments
        ) { entry ->
            PlayerPreparingAnnouncementScreen(
                teamName = entry.getString(Screens.TEAM_NAME_KEY),
                playerName = entry.getString(Screens.PLAYER_NAME_KEY)
            )
        }

        composable(route = EnterWords.value(), arguments = EnterWords.arguments) {
            val viewModel = hiltViewModel<EnterWordsViewModel>()

            EnterWordsScreen(stateHolder = viewModel.uiState)
        }

        composable(
            route = GameRoundAnnouncement.value(),
            arguments = GameRoundAnnouncement.arguments
        ) {
            GameRoundAnnouncementScreen(
                round = Gson().fromJson(
                    it.getString(Screens.GAME_ROUND_KEY),
                    GameStep.StartGameRoundStep::class.java
                )
            )
        }

        composable(route = PlayerMoveAnnouncement.value()) {
            PlayerMoveAnnouncementScreen(
                teamName = it.getString(Screens.TEAM_NAME_KEY),
                playerName = it.getString(Screens.PLAYER_NAME_KEY)
            )
        }
    }
}

private fun NavBackStackEntry.getString(key: String) = requireNotNull(arguments?.getString(key))
