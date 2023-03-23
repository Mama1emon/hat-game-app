package dev.mama1emon.hat.game

/**
 * @author Andrew Khokhlov on 22/03/2023
 */
data class GameRules(
    val wordlistSizePerPerson: Int
) {

    companion object {
        const val MIN_TEAMS_AMOUNT: Int = 2
        const val MAX_TEAMS_AMOUNT: Int = 4

        const val MIN_TEAM_NAME_LENGTH = 1
        const val MAX_TEAM_NAME_LENGTH = 10

        const val MIN_PLAYER_NAME_LENGTH = 1
        const val MAX_PLAYER_NAME_LENGTH = 10
    }
}
