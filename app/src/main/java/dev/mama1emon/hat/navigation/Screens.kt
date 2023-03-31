package dev.mama1emon.hat.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.gson.Gson
import dev.mama1emon.hat.domain.models.Player
import dev.mama1emon.hat.game.GameStep

sealed class Screens(
    route: String,
    args: List<NamedNavArgument> = listOf()
) : EntryPoint(route, args) {

    object Greeting : Screens(route = "greeting")

    object AddTeam : Screens(route = "add_team")

    object PlayerPreparingAnnouncement : Screens(
        route = "player_preparing_announcement",
        args = listOf(
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
        fun route(teamName: String, playerName: String): String {
            return Route()
                .addValue(key = TEAM_NAME_KEY, value = teamName)
                .addValue(key = PLAYER_NAME_KEY, value = playerName)
                .destination()
        }
    }

    object EnterWords : Screens(
        route = "enter_words",
        args = listOf(
            navArgument(name = PLAYER_KEY) {
                type = NavType.StringType
                nullable = false
            }
        )
    ) {
        fun route(player: Player): String {
            return Route()
                .addValue(key = PLAYER_KEY, value = Gson().toJson(player))
                .destination()
        }
    }

    object GameRoundAnnouncement : Screens(
        route = "game_round_announcement",
        args = listOf(
            navArgument(name = GAME_ROUND_KEY) {
                type = NavType.StringType
                nullable = false
            }
        )
    ) {
        fun route(round: GameStep.StartGameRoundStep): String {
            return Route()
                .addValue(key = GAME_ROUND_KEY, value = Gson().toJson(round))
                .destination()
        }
    }

    object PlayerMoveAnnouncement : Screens(
        route = "player_move_announcement",
        args = listOf(
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
        fun route(teamName: String, playerName: String): String {
            return Route()
                .addValue(key = TEAM_NAME_KEY, value = teamName)
                .addValue(key = PLAYER_NAME_KEY, value = playerName)
                .destination()
        }
    }

    companion object {
        const val PLAYER_KEY = "PLAYER_KEY"
        const val PLAYER_NAME_KEY = "PLAYER_NAME_KEY"
        const val TEAM_NAME_KEY = "TEAM_NAME_KEY"
        const val GAME_ROUND_KEY = "GAME_ROUND_KEY"
    }
}
