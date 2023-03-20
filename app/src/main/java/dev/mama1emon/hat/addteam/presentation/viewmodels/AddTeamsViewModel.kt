package dev.mama1emon.hat.addteam.presentation.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mama1emon.hat.R
import dev.mama1emon.hat.addteam.domain.models.Player
import dev.mama1emon.hat.addteam.domain.models.Team
import dev.mama1emon.hat.addteam.presentation.states.AddTeamStateHolder
import java.util.*
import javax.inject.Inject

/**
 * @author Andrew Khokhlov on 19/03/2023
 */
@HiltViewModel
internal class AddTeamsViewModel @Inject constructor() : ViewModel() {

    private val uiStateEditor = UiStateEditor()
    private val inputFieldEditor = InputFieldEditor()
    private val navigation = Navigation()

    var uiState: AddTeamStateHolder by mutableStateOf(uiStateEditor.getInitialUiState())
    var navHostController: NavHostController? = null

    private inner class UiStateEditor {

        fun getInitialUiState(): AddTeamStateHolder {
            return AddTeamStateHolder.Empty(
                navigationActions = AddTeamStateHolder.NavigationActions(
                    onBackButtonClick = navigation::popBackStack,
                    onEnterWordsButtonClick = navigation::openEnterWordsScreen
                ),
                isAddTeamAlertDrawn = false,
                addTeamAlertModel = getInitialAlertModel(),
                onAddTeamButtonClick = navigation::openAddTeamAlert,
            )
        }

        fun addTeam() {
            (uiState as? AddTeamStateHolder.ShowAlertAvailability)?.let { state ->
                uiState = when (state) {
                    is AddTeamStateHolder.Empty -> {
                        AddTeamStateHolder.NotYet(
                            enterWordsButtonEnabled = false,
                            navigationActions = state.navigationActions,
                            isAddTeamAlertDrawn = false,
                            addTeamAlertModel = state.addTeamAlertModel.copy(
                                team = Team.Factory.create(),
                                players = listOf(Player.Factory.create(), Player.Factory.create()),
                                readyButtonEnabled = false,
                            ),
                            onAddTeamButtonClick = state.onAddTeamButtonClick,
                            teams = with(state.addTeamAlertModel.team) {
                                listOf(Team.Factory.create(id = id, name = name))
                            },
                            onRemoveButtonClick = ::removeTeam
                        )
                    }
                    is AddTeamStateHolder.NotYet -> {
                        if (state.teams.size in 1 until MAX_TEAMS_AMOUNT - 1) {
                            AddTeamStateHolder.NotYet(
                                enterWordsButtonEnabled = state.enterWordsButtonEnabled,
                                navigationActions = state.navigationActions,
                                isAddTeamAlertDrawn = false,
                                addTeamAlertModel = state.addTeamAlertModel.copy(
                                    team = Team.Factory.create(),
                                    players = listOf(
                                        Player.Factory.create(),
                                        Player.Factory.create()
                                    ),
                                    readyButtonEnabled = false,
                                ),
                                onAddTeamButtonClick = state.onAddTeamButtonClick,
                                teams = state.teams + state.addTeamAlertModel.team,
                                onRemoveButtonClick = state.onRemoveButtonClick,
                            )
                        } else {
                            AddTeamStateHolder.Ready(
                                navigationActions = state.navigationActions,
                                teams = state.teams + state.addTeamAlertModel.team,
                                onRemoveButtonClick = state.onRemoveButtonClick,
                            )
                        }
                    }
                }
            }

            changeEnterWordsAvailability()
        }

        fun changeReadyButtonAvailability() {
            (uiState as? AddTeamStateHolder.ShowAlertAvailability)?.let { state ->
                uiState = state.copySealed(
                    addTeamAlertModel = state.addTeamAlertModel.copy(
                        readyButtonEnabled = with(state.addTeamAlertModel) {
                            !team.hasError && team.name.isNotBlank() &&
                                players.all { !it.hasError && it.name.isNotBlank() }
                        }
                    )
                )
            }
        }

        private fun getInitialAlertModel(): AddTeamStateHolder.ShowAlertAvailability.AddTeamAlertModel {
            return AddTeamStateHolder.ShowAlertAvailability.AddTeamAlertModel(
                team = Team.Factory.create(),
                players = listOf(Player.Factory.create(), Player.Factory.create()),
                readyButtonEnabled = false,
                onDismissRequest = navigation::closeAddTeamAlert,
                onTeamNameChanged = inputFieldEditor::changeTeamName,
                onPlayerNameChange = inputFieldEditor::changePlayerName,
                onReadyClick = uiStateEditor::addTeam
            )
        }

        private fun changeEnterWordsAvailability() {
            (uiState as? AddTeamStateHolder.NotYet)?.let {
                uiState = it.copy(
                    enterWordsButtonEnabled = it.teams.size in MIN_TEAMS_AMOUNT..MAX_TEAMS_AMOUNT
                )
            }
        }

        private fun removeTeam(teamId: UUID) {
            (uiState as? AddTeamStateHolder.RemoveTeamAvailability)?.let { state ->
                if (!state.teams.any { it.id == teamId }) {
                    error("Команды с id $teamId не существует")
                }

                uiState = when (state) {
                    is AddTeamStateHolder.NotYet -> {
                        if (state.teams.size == 1) {
                            AddTeamStateHolder.Empty(
                                navigationActions = state.navigationActions,
                                isAddTeamAlertDrawn = false,
                                addTeamAlertModel = state.addTeamAlertModel.copy(
                                    team = Team.Factory.create(),
                                    players = listOf(
                                        Player.Factory.create(),
                                        Player.Factory.create()
                                    ),
                                    readyButtonEnabled = false,
                                ),
                                onAddTeamButtonClick = state.onAddTeamButtonClick,
                            )
                        } else {
                            AddTeamStateHolder.NotYet(
                                enterWordsButtonEnabled = state.enterWordsButtonEnabled,
                                navigationActions = state.navigationActions,
                                isAddTeamAlertDrawn = false,
                                addTeamAlertModel = state.addTeamAlertModel.copy(
                                    team = Team.Factory.create(),
                                    players = listOf(
                                        Player.Factory.create(),
                                        Player.Factory.create()
                                    ),
                                    readyButtonEnabled = false,
                                ),
                                onAddTeamButtonClick = state.onAddTeamButtonClick,
                                teams = state.teams.filter { it.id != teamId },
                                onRemoveButtonClick = state.onRemoveButtonClick
                            )
                        }
                    }
                    is AddTeamStateHolder.Ready -> {
                        AddTeamStateHolder.NotYet(
                            enterWordsButtonEnabled = true,
                            navigationActions = state.navigationActions,
                            isAddTeamAlertDrawn = false,
                            addTeamAlertModel = AddTeamStateHolder.ShowAlertAvailability.AddTeamAlertModel(
                                team = Team.Factory.create(),
                                players = listOf(Player.Factory.create(), Player.Factory.create()),
                                readyButtonEnabled = false,
                                onDismissRequest = navigation::closeAddTeamAlert,
                                onTeamNameChanged = inputFieldEditor::changeTeamName,
                                onPlayerNameChange = inputFieldEditor::changePlayerName,
                                onReadyClick = ::addTeam
                            ),
                            onAddTeamButtonClick = navigation::openAddTeamAlert,
                            teams = state.teams.filter { it.id != teamId },
                            onRemoveButtonClick = state.onRemoveButtonClick
                        )
                    }
                }
            }

            changeEnterWordsAvailability()
        }
    }

    private inner class InputFieldEditor {

        fun changeTeamName(newName: String) {
            (uiState as? AddTeamStateHolder.ShowAlertAvailability)?.let { state ->
                val otherTeamsNames = when (state) {
                    is AddTeamStateHolder.Empty -> emptyList()
                    is AddTeamStateHolder.NotYet -> state.teams
                }

                uiState = state.copySealed(
                    addTeamAlertModel = state.addTeamAlertModel.copy(
                        team = if (newName.isBlank()) {
                            Team.Factory.createWithError(
                                id = state.addTeamAlertModel.team.id,
                                name = newName,
                                errorResId = R.string.empty_team_name
                            )
                        } else if (otherTeamsNames.any { it.name == newName }) {
                            Team.Factory.createWithError(
                                id = state.addTeamAlertModel.team.id,
                                name = newName,
                                errorResId = R.string.team_name_is_not_available
                            )
                        } else if (newName.length !in 1..INPUT_TEXT_MAX_LENGTH) {
                            Team.Factory.createWithError(
                                id = state.addTeamAlertModel.team.id,
                                name = newName,
                                errorResId = R.string.is_not_greather_than_ten_characters
                            )
                        } else {
                            Team.Factory.create(
                                id = state.addTeamAlertModel.team.id,
                                name = newName
                            )
                        }
                    )
                )
            }

            uiStateEditor.changeReadyButtonAvailability()
        }

        fun changePlayerName(newName: String, editableIndex: Int) {
            (uiState as? AddTeamStateHolder.ShowAlertAvailability)?.let { state ->
                uiState = state.copySealed(
                    addTeamAlertModel = state.addTeamAlertModel.copy(
                        players = state.addTeamAlertModel.players.checkEveryPlayer(
                            newName = newName,
                            editableIndex = editableIndex,
                            editablePlayer = if (newName.isBlank()) {
                                Player.Factory.createWithError(
                                    name = newName,
                                    errorResId = R.string.empty_player_name
                                )
                            } else if (newName.length !in 1..INPUT_TEXT_MAX_LENGTH) {
                                Player.Factory.createWithError(
                                    name = newName,
                                    errorResId = R.string.is_not_greather_than_ten_characters
                                )
                            } else if (state.addTeamAlertModel.players
                                    .filterIndexed { index, _ -> index != editableIndex }
                                    .any { it.name == newName }
                            ) {
                                Player.Factory.createWithError(
                                    name = newName,
                                    errorResId = R.string.player_name_is_not_available
                                )
                            } else {
                                Player.Factory.create(name = newName)
                            },
                        )
                    )
                )
            }

            uiStateEditor.changeReadyButtonAvailability()
        }

        private fun List<Player>.checkEveryPlayer(
            newName: String,
            editableIndex: Int,
            editablePlayer: Player
        ): List<Player> {
            return mapIndexed { index, player ->
                if (index == editableIndex) {
                    editablePlayer
                } else if (player.name.isBlank()) {
                    Player.Factory.createWithError(
                        name = player.name,
                        errorResId = R.string.empty_player_name
                    )
                } else if (player.name.length !in 1..INPUT_TEXT_MAX_LENGTH) {
                    Player.Factory.createWithError(
                        name = player.name,
                        errorResId = R.string.is_not_greather_than_ten_characters
                    )
                } else if (player.name == newName) {
                    Player.Factory.createWithError(
                        name = player.name,
                        errorResId = R.string.player_name_is_not_available
                    )
                } else {
                    Player.Factory.create(player.name)
                }
            }
        }
    }

    private inner class Navigation {

        fun popBackStack() {
            navHostController?.popBackStack()
        }

        fun openAddTeamAlert() {
            (uiState as? AddTeamStateHolder.ShowAlertAvailability)?.let { state ->
                val hasTeamNameError = state.addTeamAlertModel.team.hasError

                uiState = state.copySealed(
                    isAddTeamAlertDrawn = true, addTeamAlertModel = state.addTeamAlertModel.copy(
                        team = if (!hasTeamNameError) {
                            state.addTeamAlertModel.team
                        } else {
                            Team.Factory.create()
                        },
                        players = state.addTeamAlertModel.players.map { player ->
                            if (player.hasError) {
                                Player.Factory.create(player.name)
                            } else {
                                player
                            }
                        },
                    )
                )
            }

            uiStateEditor.changeReadyButtonAvailability()
        }

        fun closeAddTeamAlert() {
            (uiState as? AddTeamStateHolder.ShowAlertAvailability)?.let {
                uiState = it.copySealed(isAddTeamAlertDrawn = false)
            }
        }

        fun openEnterWordsScreen() = Unit
    }

    private companion object {
        const val INPUT_TEXT_MAX_LENGTH = 10
        const val MIN_TEAMS_AMOUNT = 2
        const val MAX_TEAMS_AMOUNT = 4
    }
}