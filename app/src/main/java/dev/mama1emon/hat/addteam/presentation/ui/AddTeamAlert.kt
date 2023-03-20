package dev.mama1emon.hat.addteam.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import dev.mama1emon.hat.R
import dev.mama1emon.hat.addteam.presentation.states.AddTeamStateHolder
import dev.mama1emon.hat.ds.components.SmallButton
import dev.mama1emon.hat.ds.components.TitleInputField
import dev.mama1emon.hat.ds.theme.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun AddTeamAlert(model: AddTeamStateHolder.ShowAlertAvailability.AddTeamAlertModel) {
    Box(
        modifier = Modifier
            .padding(horizontal = dimensionResource(id = R.dimen.padding32))
            .fillMaxWidth()
    ) {
        Dialog(
            onDismissRequest = model.onDismissRequest,
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = BlueZodiac,
                        shape = RoundedCornerShape(size = dimensionResource(id = R.dimen.radius16))
                    )
                    .padding(
                        horizontal = dimensionResource(id = R.dimen.padding24),
                        vertical = dimensionResource(id = R.dimen.padding32)
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.add_team),
                    color = White,
                    maxLines = 1,
                    style = HatTypography.Regular20
                )

                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding20)))

                val focusManager = LocalFocusManager.current
                TitleInputField(
                    title = stringResource(id = R.string.name),
                    text = model.team.name,
                    hint = stringResource(id = R.string.name),
                    onTextChanged = model.onTeamNameChanged,
                    onDone = { focusManager.moveFocus(FocusDirection.Down) },
                    isError = model.team.hasError,
                    error = model.team.errorResId?.let { stringResource(it) }
                )

                val keyboardManager = LocalSoftwareKeyboardController.current
                model.players.forEachIndexed { index, player ->
                    TitleInputField(
                        title = stringResource(id = R.string.player_with_number, index + 1),
                        text = player.name,
                        hint = stringResource(id = R.string.player_with_number, index + 1),
                        onTextChanged = { model.onPlayerNameChange(it, index) },
                        onDone = {
                            focusManager.moveFocus(FocusDirection.Down)

                            if (index == model.players.lastIndex) {
                                keyboardManager?.hide()
                            }
                        },
                        isError = player.hasError,
                        error = player.errorResId?.let { stringResource(it) }
                    )
                }

                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding10)))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(
                        space = dimensionResource(id = R.dimen.padding24)
                    )
                ) {
                    BackButton(onClick = model.onDismissRequest, modifier = Modifier.weight(1f))

                    SmallButton(
                        textId = R.string.ready,
                        enabled = model.readyButtonEnabled,
                        onClick = model.onReadyClick,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun BackButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .border(
                width = dimensionResource(R.dimen.size1),
                brush = SolidColor(value = CitrusZest),
                shape = RoundedCornerShape(corner = CornerSize(dimensionResource(R.dimen.radius8)))
            )
            .clickable(onClick = onClick),
    ) {
        Text(
            text = stringResource(R.string.back),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(vertical = dimensionResource(id = R.dimen.padding5)),
            color = White,
            maxLines = 1,
            style = HatTypography.Regular12
        )
    }
}