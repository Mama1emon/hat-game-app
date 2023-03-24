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
import dev.mama1emon.hat.LocalActivityViewModelStoreOwner
import dev.mama1emon.hat.LocalGameManager
import dev.mama1emon.hat.R
import dev.mama1emon.hat.addteam.presentation.ui.AddTeamsScreen
import dev.mama1emon.hat.addteam.presentation.viewmodels.AddTeamsViewModel
import dev.mama1emon.hat.announcement.presentation.ui.AnnouncementScreen
import dev.mama1emon.hat.ds.theme.CitrusZest
import dev.mama1emon.hat.greeting.presentation.ui.GreetingScreen
import dev.mama1emon.hat.navigation.Screens.*
import java.util.*

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
            route = PlayerAttention.value(),
            arguments = PlayerAttention.arguments
        ) { entry ->
            val playerName = entry.getString(Screens.PLAYER_NAME_KEY)

            val gameManager = LocalGameManager.current
            AnnouncementScreen(
                titleResId = R.string.attention,
                imageResId = R.drawable.ill_bell_alarm,
                description = buildAnnotatedString {
                    val strings = stringArrayResource(
                        id = R.array.give_the_device_to_specified_player
                    )
                    append(text = requireNotNull(value = strings.getOrNull(index = 0)))
                    withStyle(style = SpanStyle(CitrusZest)) {
                        append(text = playerName)
                    }
                    append(text = requireNotNull(value = strings.getOrNull(index = 1)))
                    withStyle(style = SpanStyle(CitrusZest)) {
                        append(text = entry.getString(Screens.TEAM_NAME_KEY))
                    }
                    append(text = requireNotNull(value = strings.getOrNull(index = 2)))
                },
                buttonText = stringResource(id = R.string.i_am_player_with_name, playerName),
                onButtonClick = {
                    gameManager.startPlayerPreparing(
                        teamId = UUID.fromString(entry.getString(Screens.TEAM_ID_KEY)),
                        playerId = UUID.fromString(entry.getString(Screens.PLAYER_ID_KEY))
                    )
                }
            )
        }
    }
}

private fun NavBackStackEntry.getString(key: String) = requireNotNull(arguments?.getString(key))
