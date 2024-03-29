package dev.mama1emon.hat.features.addteam.presentation.states

import dev.mama1emon.hat.features.addteam.presentation.models.TeamField
import java.util.*

sealed interface RemoveTeamAvailability {
    val teams: List<TeamField>
    val onRemoveButtonClick: (UUID) -> Unit
}