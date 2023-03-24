package dev.mama1emon.hat.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import java.util.*

sealed class Screens(
    route: String,
    args: List<NamedNavArgument> = listOf()
) : EntryPoint(route, args) {

    object Greeting : Screens(route = "greeting")

    object AddTeam : Screens(route = "add_team")

    object PlayerAttention : Screens(
        route = "player_attention",
        args = listOf(
            navArgument(name = TEAM_ID_KEY) {
                type = NavType.StringType
                nullable = false
            },
            navArgument(name = PLAYER_ID_KEY) {
                type = NavType.StringType
                nullable = false
            },
            navArgument(name = TEAM_NAME_KEY) {
                type = NavType.StringType
                nullable = false
            },
            navArgument(name = PLAYER_NAME_KEY) {
                type = NavType.StringType
                nullable = false
            }
        )
    ) {
        fun route(teamId: UUID, playerId: UUID, teamName: String, playerName: String): String {
            return Route()
                .addValue(key = TEAM_ID_KEY, value = teamId.toString())
                .addValue(key = PLAYER_ID_KEY, value = playerId.toString())
                .addValue(key = TEAM_NAME_KEY, value = teamName)
                .addValue(key = PLAYER_NAME_KEY, value = playerName)
                .destination()
        }
    }

    companion object {
        const val PLAYER_ID_KEY = "PLAYER_ID_KEY"
        const val PLAYER_NAME_KEY = "PLAYER_NAME_KEY"

        const val TEAM_ID_KEY = "TEAM_ID_KEY"
        const val TEAM_NAME_KEY = "TEAM_NAME_KEY"
    }
}
