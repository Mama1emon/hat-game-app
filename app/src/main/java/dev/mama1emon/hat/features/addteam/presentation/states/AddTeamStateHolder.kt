package dev.mama1emon.hat.features.addteam.presentation.states

import dev.mama1emon.hat.features.addteam.presentation.models.TeamField
import java.util.*

/**
 * @author Andrew Khokhlov on 19/03/2023
 */
sealed interface AddTeamStateHolder {
    val enterWordsButtonEnabled: Boolean
    val onEnterWordsButtonClick: () -> Unit

    data class Empty(
        override val isAddTeamAlertDrawn: Boolean,
        override val addTeamAlertModel: ShowAlertAvailability.AddTeamAlertModel,
        override val onAddTeamButtonClick: () -> Unit
    ) : AddTeamStateHolder, ShowAlertAvailability {

        override val enterWordsButtonEnabled: Boolean = false
        override val onEnterWordsButtonClick: () -> Unit = { }
    }

    data class NotYet(
        override val isAddTeamAlertDrawn: Boolean,
        override val addTeamAlertModel: ShowAlertAvailability.AddTeamAlertModel,
        override val onAddTeamButtonClick: () -> Unit,
        override val teams: List<TeamField>,
        override val onRemoveButtonClick: (UUID) -> Unit,
        override val enterWordsButtonEnabled: Boolean,
        override val onEnterWordsButtonClick: () -> Unit,
    ) : AddTeamStateHolder, ShowAlertAvailability, RemoveTeamAvailability

    data class Ready(
        override val teams: List<TeamField>,
        override val onRemoveButtonClick: (UUID) -> Unit,
        override val onEnterWordsButtonClick: () -> Unit,
    ) : AddTeamStateHolder, RemoveTeamAvailability {

        override val enterWordsButtonEnabled: Boolean = true
    }
}
