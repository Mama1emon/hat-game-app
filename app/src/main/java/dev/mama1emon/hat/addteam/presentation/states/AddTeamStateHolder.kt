package dev.mama1emon.hat.addteam.presentation.states

import dev.mama1emon.hat.addteam.domain.models.Player
import dev.mama1emon.hat.addteam.domain.models.Team
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
            val team: Team,
            val players: List<Player>,
            val readyButtonEnabled: Boolean,
            val onDismissRequest: () -> Unit,
            val onTeamNameChanged: (String) -> Unit,
            val onPlayerNameChange: (String, Int) -> Unit,
            val onReadyClick: () -> Unit
        )
    }

    sealed interface RemoveTeamAvailability {
        val teams: List<Team>
        val onRemoveButtonClick: (UUID) -> Unit
    }

    data class Empty(
        override val navigationActions: NavigationActions,
        override val isAddTeamAlertDrawn: Boolean,
        override val addTeamAlertModel: ShowAlertAvailability.AddTeamAlertModel,
        override val onAddTeamButtonClick: () -> Unit
    ) : AddTeamStateHolder, ShowAlertAvailability

    data class NotYet(
        val enterWordsButtonEnabled: Boolean,
        override val navigationActions: NavigationActions,
        override val isAddTeamAlertDrawn: Boolean,
        override val addTeamAlertModel: ShowAlertAvailability.AddTeamAlertModel,
        override val onAddTeamButtonClick: () -> Unit,
        override val teams: List<Team>,
        override val onRemoveButtonClick: (UUID) -> Unit
    ) : AddTeamStateHolder, ShowAlertAvailability, RemoveTeamAvailability

    data class Ready(
        override val navigationActions: NavigationActions,
        override val teams: List<Team>,
        override val onRemoveButtonClick: (UUID) -> Unit,
    ) : AddTeamStateHolder, RemoveTeamAvailability

    data class NavigationActions(
        val onBackButtonClick: () -> Unit,
        val onEnterWordsButtonClick: () -> Unit
    )
}
