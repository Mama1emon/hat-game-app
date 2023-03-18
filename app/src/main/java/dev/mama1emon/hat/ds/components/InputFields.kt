package dev.mama1emon.hat.ds.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import dev.mama1emon.hat.R
import dev.mama1emon.hat.ds.theme.BattleshipGrey
import dev.mama1emon.hat.ds.theme.CitrusZest
import dev.mama1emon.hat.ds.theme.HatTypography
import dev.mama1emon.hat.ds.theme.White

private const val PADDING_BETWEEN_CURSOR_AND_TEXT = 1.6

@Composable
fun TitleInputField(
    @StringRes titleId: Int,
    text: String,
    @StringRes hintId: Int,
    onTextChanged: (String) -> Unit,
    onDone: KeyboardActionScope.() -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = titleId),
            modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding10)),
            color = White,
            maxLines = 1,
            style = HatTypography.Regular16
        )

        EditableTextField(
            text = text,
            hintId = hintId,
            onTextChanged = onTextChanged,
            onDone = onDone
        )
    }
}

@Composable
fun EditableTextField(
    text: String,
    @StringRes hintId: Int,
    onTextChanged: (String) -> Unit,
    onDone: KeyboardActionScope.() -> Unit
) {
    BasicTextField(
        value = text,
        onValueChange = onTextChanged,
        modifier = Modifier.width(dimensionResource(id = R.dimen.size146)),
        textStyle = HatTypography.Regular16.copy(color = White),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = onDone),
        singleLine = true,
        maxLines = 1,
        cursorBrush = SolidColor(CitrusZest)
    ) { innerTextField ->
        Column(verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding2))) {
            Box {
                if (text.isBlank()) {
                    Text(
                        text = stringResource(id = hintId),
                        modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding2)),
                        color = BattleshipGrey,
                        maxLines = 1,
                        style = HatTypography.Regular16
                    )
                }
                innerTextField()
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(PADDING_BETWEEN_CURSOR_AND_TEXT.dp)
                    .background(CitrusZest)
            )
        }
    }
}