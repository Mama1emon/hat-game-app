package dev.mama1emon.hat.announcement.move.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import dev.mama1emon.hat.LocalGameManager
import dev.mama1emon.hat.R
import dev.mama1emon.hat.announcement.presentation.ui.AnnouncementComponent
import dev.mama1emon.hat.ds.theme.CitrusZest

/**
 * @author Andrew Khokhlov on 31/03/2023
 */
@Composable
fun PlayerMoveAnnouncementScreen(teamName: String, playerName: String) {
    val gameManager = LocalGameManager.current

    AnnouncementComponent(
        title = teamName,
        imageResId = R.drawable.ill_bell_alarm,
        description = buildAnnotatedString {
            val strings = stringArrayResource(
                id = R.array.give_the_device_to_specified_player_for_guessing_words
            )
            append(requireNotNull(strings.getOrNull(index = 0)))

            withStyle(style = SpanStyle(CitrusZest)) {
                append(text = playerName)
            }

            append(requireNotNull(strings.getOrNull(index = 1)))
        },
        buttonText = stringResource(id = R.string.i_am_player_with_name, playerName),
        onButtonClick = gameManager::startPlayerMove,
    )
}