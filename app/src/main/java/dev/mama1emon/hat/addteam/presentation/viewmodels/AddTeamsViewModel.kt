package dev.mama1emon.hat.addteam.presentation.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mama1emon.hat.R
import dev.mama1emon.hat.addteam.presentation.models.PlayerField
import dev.mama1emon.hat.addteam.presentation.models.TeamField
import dev.mama1emon.hat.addteam.presentation.states.AddTeamStateHolder
import dev.mama1emon.hat.domain.models.Player
import dev.mama1emon.hat.domain.models.Team
import dev.mama1emon.hat.game.GameManager
import dev.mama1emon.hat.game.GameRules
import java.util.*
import javax.inject.Inject

/**
 * @author Andrew Khokhlov on 19/03/2023
 */
@HiltViewModel
internal class AddTeamsViewModel @Inject constructor(
    private val gameManager: GameManager
) : ViewModel() {

    private val uiStateEditor = UiStateEditor()
    private val inputFieldEditor = InputFieldEditor()
    private val navigation = Navigation()

    var uiState: AddTeamStateHolder by mutableStateOf(uiStateEditor.getInitialUiState())
        private set

    private val teams = mutableListOf<Team>()

    private inner class UiStateEditor {

        fun getInitialUiState(): AddTeamStateHolder {
            return AddTeamStateHolder.Empty(
                navigationActions = AddTeamStateHolder.NavigationActions(
                    onEnterWordsButtonClick = { gameManager.finishTeamsPreparing(teams) }
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
                                team = TeamField.Factory.create(),
                                players = listOf(
                                    PlayerField.Factory.create(),
                                    PlayerField.Factory.create()
                                ),
                                readyButtonEnabled = false,
                            ),
                            onAddTeamButtonClick = state.onAddTeamButtonClick,
                            teams = with(state.addTeamAlertModel.team) {
                                listOf(TeamField.Factory.create(id = id, name = name))
                            },
                            onRemoveButtonClick = ::removeTeam
                        )
                    }
                    is AddTeamStateHolder.NotYet -> {
                        if (state.teams.size in 1 until GameRules.MAX_TEAMS_AMOUNT - 1) {
                            AddTeamStateHolder.NotYet(
                                enterWordsButtonEnabled = state.enterWordsButtonEnabled,
                                navigationActions = state.navigationActions,
                                isAddTeamAlertDrawn = false,
                                addTeamAlertModel = state.addTeamAlertModel.copy(
                                    team = TeamField.Factory.create(),
                                    players = listOf(
                                        PlayerField.Factory.create(),
                                        PlayerField.Factory.create()
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

                with(state.addTeamAlertModel) {
                    teams.add(
                        Team(
                            id = team.id,
                            name = team.name,
                            players = players.map { playerField ->
                                Player(id = playerField.id, name = playerField.name)
                            }
                        )
                    )
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
                team = TeamField.Factory.create(),
                players = listOf(PlayerField.Factory.create(), PlayerField.Factory.create()),
                readyButtonEnabled = false,
                onDismissRequest = navigation::closeAddTeamAlert,
                onTeamNameChanged = inputFieldEditor::changeTeamName,
                onPlayerNameChange = inputFieldEditor::changePlayerName,
                onReadyClick = uiStateEditor::addTeam
            )
        }

        private fun changeEnterWordsAvailability() {
            (uiState as? AddTeamStateHolder.NotYet)?.let { state ->
                val teamAmountRange = GameRules.MIN_TEAMS_AMOUNT..GameRules.MAX_TEAMS_AMOUNT
                uiState = state.copy(
                    enterWordsButtonEnabled = state.teams.size in teamAmountRange
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
                                    team = TeamField.Factory.create(),
                                    players = listOf(
                                        PlayerField.Factory.create(),
                                        PlayerField.Factory.create()
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
                                    team = TeamField.Factory.create(),
                                    players = listOf(
                                        PlayerField.Factory.create(),
                                        PlayerField.Factory.create()
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
                                team = TeamField.Factory.create(),
                                players = listOf(
                                    PlayerField.Factory.create(),
                                    PlayerField.Factory.create()
                                ),
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

                teams.removeAt(index = state.teams.indexOfFirst { it.id == teamId })
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

                val isNotValidTeamName by lazy {
                    newName.length !in GameRules.MIN_TEAM_NAME_LENGTH
                        .rangeTo(GameRules.MAX_TEAM_NAME_LENGTH)
                }

                uiState = state.copySealed(
                    addTeamAlertModel = state.addTeamAlertModel.copy(
                        team = if (newName.isBlank()) {
                            TeamField.Factory.createWithError(
                                id = state.addTeamAlertModel.team.id,
                                name = newName,
                                errorResId = R.string.empty_team_name
                            )
                        } else if (otherTeamsNames.any { it.name == newName }) {
                            TeamField.Factory.createWithError(
                                id = state.addTeamAlertModel.team.id,
                                name = newName,
                                errorResId = R.string.team_name_is_not_available
                            )
                        } else if (isNotValidTeamName) {
                            TeamField.Factory.createWithError(
                                id = state.addTeamAlertModel.team.id,
                                name = newName,
                                errorResId = R.string.is_not_greather_than_ten_characters
                            )
                        } else {
                            TeamField.Factory.create(
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

                val isNotValidPlayerName by lazy {
                    newName.length !in GameRules.MIN_PLAYER_NAME_LENGTH
                        .rangeTo(GameRules.MAX_PLAYER_NAME_LENGTH)
                }

                val editablePlayer = requireNotNull(
                    value = state.addTeamAlertModel.players.getOrNull(editableIndex)
                )
                uiState = state.copySealed(
                    addTeamAlertModel = state.addTeamAlertModel.copy(
                        players = state.addTeamAlertModel.players.checkEveryPlayer(
                            newName = newName,
                            editableIndex = editableIndex,
                            editablePlayer = if (newName.isBlank()) {
                                PlayerField.Factory.createWithError(
                                    id = editablePlayer.id,
                                    name = newName,
                                    errorResId = R.string.empty_player_name
                                )
                            } else if (isNotValidPlayerName) {
                                PlayerField.Factory.createWithError(
                                    id = editablePlayer.id,
                                    name = newName,
                                    errorResId = R.string.is_not_greather_than_ten_characters
                                )
                            } else if (state.addTeamAlertModel.players
                                    .filterIndexed { index, _ -> index != editableIndex }
                                    .any { it.name == newName }
                            ) {
                                PlayerField.Factory.createWithError(
                                    id = editablePlayer.id,
                                    name = newName,
                                    errorResId = R.string.player_name_is_not_available
                                )
                            } else {
                                PlayerField.Factory.create(
                                    id = editablePlayer.id,
                                    name = newName
                                )
                            },
                        )
                    )
                )
            }

            uiStateEditor.changeReadyButtonAvailability()
        }

        private fun List<PlayerField>.checkEveryPlayer(
            newName: String,
            editableIndex: Int,
            editablePlayer: PlayerField
        ): List<PlayerField> {
            return mapIndexed { index, player ->
                val isNotValidPlayerName by lazy {
                    player.name.length !in GameRules.MIN_PLAYER_NAME_LENGTH
                        .rangeTo(GameRules.MAX_PLAYER_NAME_LENGTH)
                }

                if (index == editableIndex) {
                    editablePlayer
                } else if (player.name.isBlank()) {
                    PlayerField.Factory.createWithError(
                        id = editablePlayer.id,
                        name = player.name,
                        errorResId = R.string.empty_player_name
                    )
                } else if (isNotValidPlayerName) {
                    PlayerField.Factory.createWithError(
                        id = editablePlayer.id,
                        name = player.name,
                        errorResId = R.string.is_not_greather_than_ten_characters
                    )
                } else if (player.name == newName) {
                    PlayerField.Factory.createWithError(
                        id = editablePlayer.id,
                        name = player.name,
                        errorResId = R.string.player_name_is_not_available
                    )
                } else {
                    PlayerField.Factory.create(
                        id = editablePlayer.id,
                        name = player.name
                    )
                }
            }
        }
    }

    private inner class Navigation {

        fun openAddTeamAlert() {
            (uiState as? AddTeamStateHolder.ShowAlertAvailability)?.let { state ->
                uiState = state.copySealed(
                    isAddTeamAlertDrawn = true,
                    addTeamAlertModel = state.addTeamAlertModel.copy(
                        team = if (!state.addTeamAlertModel.team.hasError) {
                            state.addTeamAlertModel.team
                        } else {
                            TeamField.Factory.create()
                        },
                        players = state.addTeamAlertModel.players.map { player ->
                            if (!player.hasError) {
                                player
                            } else {
                                PlayerField.Factory.create()
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
    }
}