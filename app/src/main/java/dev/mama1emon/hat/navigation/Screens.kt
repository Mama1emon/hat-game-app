package dev.mama1emon.hat.navigation

import androidx.navigation.NamedNavArgument

sealed class Screens(val route: String, val args: List<NamedNavArgument> = listOf()) {

    object Greeting : Screens(route = "greeting")

    object AddTeam : Screens(route = "add_team")

    object Announcement : Screens(route = "announcement")

    object EnterWords : Screens(route = "enter_words")
}
