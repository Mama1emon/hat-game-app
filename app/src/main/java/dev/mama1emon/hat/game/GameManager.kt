package dev.mama1emon.hat.game

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.mama1emon.hat.domain.models.Team
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Andrew Khokhlov on 22/03/2023
 */
@Singleton
class GameManager @Inject constructor() {

    val rules: GameRules = GameRules(wordlistSizePerPerson = 5)
    var currentStep: GameStep by mutableStateOf(GameStep.Greeting)
        private set

    private var teams: List<Team> = emptyList()
    private val readyPlayerIds = mutableListOf<UUID>()

    init {
        logEvents("Меню открыто")
    }

    fun startTeamsPreparing() {
        currentStep = GameStep.StartTeamsPreparingStep
        logEvents("Начать подготовку команд")
    }

    fun finishTeamsPreparing(teams: List<Team>) {
        this.teams = teams
        readyPlayerIds.clear()
        logEvents("Закончить подготовку команд")

        val team = requireNotNull(teams.firstOrNull())
        val player = requireNotNull(team.players.firstOrNull())
        logEvents("Начать подготовку игроков")

        currentStep = GameStep.FinishTeamsPreparingStep(
            teamId = team.id,
            playerId = player.id,
            teamName = team.name,
            playerName = player.name,
        )

        logEvents("Подготовка игрока с именем ${player.name} из команды ${team.name}")
    }

    fun startPlayerPreparing(teamId: UUID, playerId: UUID) {
        val team = requireNotNull(teams.firstOrNull { it.id == teamId })
        val player = requireNotNull(team.players.firstOrNull { it.id == playerId })

        currentStep = GameStep.StartPlayerPreparingStep(
            teamId = teamId,
            playerId = playerId,
            playerName = player.name,
        )
        logEvents("Игрок с именем ${player.name} из команды ${team.name} приступил к вводу слов")
    }

    fun finishPlayerPreparing(wordlist: List<String>) {
        val step = requireNotNull(currentStep as? GameStep.StartPlayerPreparingStep)
        val finishedTeam = requireNotNull(teams.firstOrNull { it.id == step.teamId })
        val finishedPlayer = requireNotNull(
            value = finishedTeam.players.firstOrNull { it.id == step.playerId }
        )
        logEvents(
            message = "Игрок с именем ${finishedPlayer.name} из команды ${finishedTeam.name} " +
                "завершил ввод слов"
        )

        teams = teams.map { team ->
            if (team.id == step.teamId) {
                team.copy(
                    players = team.players.map { player ->
                        if (player.name == step.playerName) {
                            player.copy(wordlist = wordlist)
                        } else {
                            player
                        }
                    }
                )
            } else {
                team
            }
        }
        readyPlayerIds.add(finishedPlayer.id)

        val nextTeam = teams.firstOrNull { nextTeam ->
            nextTeam.players.any { it.id !in readyPlayerIds }
        }
        if (nextTeam != null) {
            val nextPlayer = requireNotNull(
                value = nextTeam.players.firstOrNull { it.id !in readyPlayerIds }
            )
            currentStep = GameStep.FinishPlayerPreparingStep(
                teamId = nextTeam.id,
                playerId = nextPlayer.id,
                teamName = nextTeam.name,
                playerName = nextPlayer.name
            )
            logEvents("Подготовка игрока с именем ${nextPlayer.name} из команды ${nextTeam.name}")
        } else {
            currentStep = GameStep.StartGame
            logEvents("Начало игры")
        }
    }

    fun cancelPreparing() {
        currentStep = GameStep.Greeting
        logEvents("Отмена подготовки")
    }

    private fun logEvents(message: String) {
        Log.d("NavigationLog", "----> ${requireNotNull(currentStep::class.simpleName)}")
        Log.d("EventsLog", message)
    }
}