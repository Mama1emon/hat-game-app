package dev.mama1emon.hat.addteam.presentation.states

import dev.mama1emon.hat.addteam.presentation.models.PlayerField
import dev.mama1emon.hat.addteam.presentation.models.TeamField

sealed interface ShowAlertAvailability {
    val isAddTeamAlertDrawn: Boolean
    val addTeamAlertModel: AddTeamAlertModel
    val onAddTeamButtonClick: () -> Unit

    fun copySealed(
        isAddTeamAlertDrawn: Boolean = this.isAddTeamAlertDrawn,
        addTeamAlertModel: AddTeamAlertModel = this.addTeamAlertModel,
        onAddTeamButtonClick: () -> Unit = this.onAddTeamButtonClick
    ): AddTeamStateHolder {
        return when (this) {
            is AddTeamStateHolder.Empty -> copy(
                isAddTeamAlertDrawn = isAddTeamAlertDrawn,
                addTeamAlertModel = addTeamAlertModel,
                onAddTeamButtonClick = onAddTeamButtonClick
            )
            is AddTeamStateHolder.NotYet -> copy(
                isAddTeamAlertDrawn = isAddTeamAlertDrawn,
                addTeamAlertModel = addTeamAlertModel,
                onAddTeamButtonClick = onAddTeamButtonClick
            )
        }
    }

    data class AddTeamAlertModel(
        val team: TeamField,
        val players: List<PlayerField>,
        val readyButtonEnabled: Boolean,
        val onDismissRequest: () -> Unit,
        val onTeamNameChanged: (String) -> Unit,
        val onPlayerNameChange: (String, Int) -> Unit,
        val onReadyClick: () -> Unit
    )
}