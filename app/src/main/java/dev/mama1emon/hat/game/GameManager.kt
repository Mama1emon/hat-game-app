package dev.mama1emon.hat.game

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.mama1emon.hat.domain.models.Player
import dev.mama1emon.hat.domain.models.Team
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.properties.Delegates

/**
 * @author Andrew Khokhlov on 22/03/2023
 */
@Singleton
class GameManager @Inject constructor() {

    val rules: GameRules = GameRules(wordlistSizePerPerson = 5)
    var currentStep: GameStep by mutableStateOf(GameStep.Greeting)
        private set

    private var savedTeams: Set<Team> = emptySet()
    private val readyPlayerIds = mutableListOf<UUID>()
    private var currentPlayer: Player by Delegates.notNull()

    init {
        logEvents("Меню открыто")
    }

    fun startTeamsPreparing() {
        currentStep = GameStep.StartTeamsPreparingStep
        logEvents("Начать подготовку команд")
    }

    fun finishTeamsPreparing(teams: Set<Team>) {
        savedTeams = savedTeams + teams.filterNot { it.id in savedTeams.map(Team::id) }
        readyPlayerIds.clear()
        logEvents("Закончить подготовку команд")

        val team = requireNotNull(savedTeams.firstOrNull())
        val player = requireNotNull(team.players.firstOrNull())
        logEvents("Начать подготовку игроков")

        currentStep = GameStep.FinishTeamsPreparingStep(
            teamName = team.name,
            playerName = player.name,
        )
        currentPlayer = player

        logEvents("Подготовка игрока с именем ${player.name} из команды ${team.name}")
    }

    fun startPlayerPreparing() {
        val teamName = requireNotNull(
            value = savedTeams.firstOrNull { team -> team.players.any { it == currentPlayer } }?.name
        )

        currentStep = GameStep.StartPlayerPreparingStep(player = currentPlayer)
        logEvents("Игрок с именем ${currentPlayer.name} из команды $teamName приступил к вводу слов")
    }

    fun finishPlayerPreparing(wordlist: List<String>) {
        val finishedTeam = requireNotNull(
            value = savedTeams.firstOrNull { team -> team.players.any { it == currentPlayer } }
        )

        logEvents(
            message = "Игрок с именем ${currentPlayer.name} из команды ${finishedTeam.name} " +
                "завершил ввод слов"
        )

        savedTeams = savedTeams
            .map { team ->
                if (team.id == finishedTeam.id) {
                    team.copy(
                        players = team.players.map { player ->
                            if (player.id == currentPlayer.id) {
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
            .toSet()

        readyPlayerIds.add(currentPlayer.id)

        val nextTeam = savedTeams.firstOrNull { nextTeam ->
            nextTeam.players.any { it.id !in readyPlayerIds }
        }
        if (nextTeam != null) {
            val nextPlayer = requireNotNull(
                value = nextTeam.players.firstOrNull { it.id !in readyPlayerIds }
            )
            currentStep = GameStep.FinishPlayerPreparingStep(
                teamName = nextTeam.name,
                playerName = nextPlayer.name
            )
            currentPlayer = nextPlayer
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