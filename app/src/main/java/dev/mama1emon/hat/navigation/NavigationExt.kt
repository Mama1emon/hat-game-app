package dev.mama1emon.hat.navigation

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation

const val HAT_GRAPH_NAME = "hat"

val LocalNavController = compositionLocalOf<NavHostController>() {
    error("NavHostController value not specified")
}

fun NavGraphBuilder.hatGraph() {
    navigation(
        route = HAT_GRAPH_NAME,
        startDestination = Screens.Greeting.route
    ) {
        composable(route = Screens.Greeting.route) {
        }
    }
}
