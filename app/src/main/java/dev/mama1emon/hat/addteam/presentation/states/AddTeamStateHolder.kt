package dev.mama1emon.hat.addteam.presentation.states

import dev.mama1emon.hat.addteam.domain.models.Player
import dev.mama1emon.hat.addteam.domain.models.Team

/**
 * @author Andrew Khokhlov on 19/03/2023
 */
internal sealed interface AddTeamStateHolder {

    sealed interface AvailableChangeTeamsList {
        val isAddTeamAlertDrawn: Boolean
        val addTeamAlertModel: AddTeamAlertModel
    }

    data class Empty(
        val actions: Actions,
        override val isAddTeamAlertDrawn: Boolean,
        override val addTeamAlertModel: AddTeamAlertModel
    ) : AddTeamStateHolder, AvailableChangeTeamsList {

        data class Actions(
            val onBackButtonClick: () -> Unit,
            val onAddTeamButtonClick: () -> Unit,
            val onEnterWordsButtonClick: () -> Unit
        )
    }

    data class NotYet(
        val team: Team,
        val actions: Actions,
        override val isAddTeamAlertDrawn: Boolean,
        override val addTeamAlertModel: AddTeamAlertModel
    ) : AddTeamStateHolder, AvailableChangeTeamsList

    data class Ready(val teams: List<Team>, val actions: Actions) : AddTeamStateHolder

    data class Actions(
        val onBackButtonClick: () -> Unit,
        val onAddTeamButtonClick: () -> Unit,
        val onRemoveButtonClick: (Int) -> Unit,
        val onEnterWordsButtonClick: () -> Unit
    )

    data class AddTeamAlertModel(
        val team: Team,
        val players: List<Player>,
        val readyButtonEnabled: Boolean,
        val onDismissRequest: () -> Unit,
        val onTeamNameChanged: (String) -> Unit,
        val onPlayerNameChange: (String, Int) -> Unit,
        val onReadyClick: () -> Unit
    )
}
