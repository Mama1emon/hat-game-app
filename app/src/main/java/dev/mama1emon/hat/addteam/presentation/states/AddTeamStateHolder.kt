package dev.mama1emon.hat.addteam.presentation.states

import dev.mama1emon.hat.addteam.presentation.models.PlayerField
import dev.mama1emon.hat.addteam.presentation.models.TeamField
import java.util.*

/**
 * @author Andrew Khokhlov on 19/03/2023
 */
internal sealed interface AddTeamStateHolder {
    val navigationActions: NavigationActions

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
                is Empty -> copy(
                    isAddTeamAlertDrawn = isAddTeamAlertDrawn,
                    addTeamAlertModel = addTeamAlertModel,
                    onAddTeamButtonClick = onAddTeamButtonClick
                )
                is NotYet -> copy(
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

    sealed interface RemoveTeamAvailability {
        val teams: List<TeamField>
        val onRemoveButtonClick: (UUID) -> Unit
    }

    data class Empty(
        override val navigationActions: NavigationActions,
        override val isAddTeamAlertDrawn: Boolean,
        override val addTeamAlertModel: ShowAlertAvailability.AddTeamAlertModel,
        override val onAddTeamButtonClick: () -> Unit
    ) : AddTeamStateHolder, ShowAlertAvailability

    data class NotYet(
        override val navigationActions: NavigationActions,
        override val isAddTeamAlertDrawn: Boolean,
        override val addTeamAlertModel: ShowAlertAvailability.AddTeamAlertModel,
        override val onAddTeamButtonClick: () -> Unit,
        override val teams: List<TeamField>,
        override val onRemoveButtonClick: (UUID) -> Unit,
        val enterWordsButtonEnabled: Boolean
    ) : AddTeamStateHolder, ShowAlertAvailability, RemoveTeamAvailability

    data class Ready(
        override val navigationActions: NavigationActions,
        override val teams: List<TeamField>,
        override val onRemoveButtonClick: (UUID) -> Unit,
    ) : AddTeamStateHolder, RemoveTeamAvailability

    data class NavigationActions(val onEnterWordsButtonClick: () -> Unit)
}
