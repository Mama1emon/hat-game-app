package dev.mama1emon.hat.announcement.round.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import dev.mama1emon.hat.LocalGameManager
import dev.mama1emon.hat.R
import dev.mama1emon.hat.announcement.presentation.ui.AnnouncementScreen
import dev.mama1emon.hat.game.GameStep

/**
 * @author Andrew Khokhlov on 31/03/2023
 */
@Composable
fun GameRoundAnnouncementScreen(round: GameStep.StartGameRoundStep) {
    val gameManager = LocalGameManager.current

    AnnouncementScreen(
        title = stringResource(
            id = when (round) {
                GameStep.StartGameRoundStep.First -> R.string.eloquence
                GameStep.StartGameRoundStep.Second -> R.string.conciseness
                GameStep.StartGameRoundStep.Third -> R.string.artistry
            }
        ),
        imageResId = when (round) {
            GameStep.StartGameRoundStep.First -> R.drawable.ill_megaphone
            GameStep.StartGameRoundStep.Second -> R.drawable.ill_aim_focus_arrow
            GameStep.StartGameRoundStep.Third -> R.drawable.ill_thumb_up_like
        },
        description = AnnotatedString(
            text = stringResource(
                id = when (round) {
                    GameStep.StartGameRoundStep.First -> R.string.explain_using_sounds_and_words
                    GameStep.StartGameRoundStep.Second -> R.string.explain_using_only_one_word
                    GameStep.StartGameRoundStep.Third -> R.string.explain_using_gestures_and_facial_expressions
                }
            )
        ),
        buttonText = stringResource(id = R.string.here_goes),
        onButtonClick = gameManager::startPlayerMove,
    )
}