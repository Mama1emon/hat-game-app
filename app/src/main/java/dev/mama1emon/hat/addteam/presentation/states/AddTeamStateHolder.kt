package dev.mama1emon.hat.addteam.presentation.states

import dev.mama1emon.hat.addteam.domain.models.Team

/**
 * @author Andrew Khokhlov on 19/03/2023
 */
internal sealed interface AddTeamStateHolder {

    data class Empty(val actions: Actions) : AddTeamStateHolder {

        data class Actions(
            val onBackButtonClick: () -> Unit,
            val onAddTeamButtonClick: () -> Unit,
            val onEnterWordsButtonClick: () -> Unit
        )
    }

    data class NotYet(val team: Team, val actions: Actions) : AddTeamStateHolder

    data class Ready(val teams: List<Team>, val actions: Actions) : AddTeamStateHolder

    data class Actions(
        val onBackButtonClick: () -> Unit,
        val onAddTeamButtonClick: () -> Unit,
        val onRemoveButtonClick: (Int) -> Unit,
        val onEnterWordsButtonClick: () -> Unit
    )
}
