package dev.mama1emon.hat.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import dev.mama1emon.hat.LocalActivityViewModelStoreOwner
import dev.mama1emon.hat.addteam.presentation.ui.AddTeamsScreen
import dev.mama1emon.hat.addteam.presentation.viewmodels.AddTeamsViewModel
import dev.mama1emon.hat.greeting.presentation.ui.GreetingScreen

const val HAT_GRAPH_NAME = "hat"

fun NavGraphBuilder.hatGraph() {
    navigation(route = HAT_GRAPH_NAME, startDestination = Screens.Greeting.route) {
        composable(route = Screens.Greeting.route) {
            GreetingScreen()
        }

        composable(route = Screens.AddTeam.route) {
            val viewModel = hiltViewModel<AddTeamsViewModel>(
                viewModelStoreOwner = LocalActivityViewModelStoreOwner.current
            )

            AddTeamsScreen(stateHolder = viewModel.uiState)
        }
    }
}
