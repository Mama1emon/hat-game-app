package dev.mama1emon.hat.ds.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import dev.mama1emon.hat.R
import dev.mama1emon.hat.ds.theme.BattleshipGrey
import dev.mama1emon.hat.ds.theme.CitrusZest
import dev.mama1emon.hat.ds.theme.HatTypography
import dev.mama1emon.hat.ds.theme.White

private const val PADDING_BETWEEN_CURSOR_AND_TEXT = 1.6

@Composable
fun TitleInputField(
    title: String,
    text: String,
    hint: String,
    onTextChanged: (String) -> Unit,
    onDone: KeyboardActionScope.() -> Unit,
    isError: Boolean = false,
    error: String? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = title,
            color = White,
            maxLines = 1,
            style = HatTypography.Regular16
        )

        EditableTextField(
            text = text,
            hint = hint,
            onTextChanged = onTextChanged,
            onDone = onDone,
            isError = isError,
            error = error
        )
    }
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun EditableTextField(
    text: String,
    hint: String,
    onTextChanged: (String) -> Unit,
    onDone: KeyboardActionScope.() -> Unit,
    isError: Boolean = false,
    error: String? = null
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
        cursorBrush = if (isError) SolidColor(Color.Red) else SolidColor(CitrusZest)
    ) { innerTextField ->
        Column(verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding2))) {
            Box {
                if (text.isBlank()) {
                    Text(
                        text = hint,
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
                    .background(if (isError) SolidColor(Color.Red) else SolidColor(CitrusZest))
            )

            Text(
                text = error ?: "",
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding2)),
                color = if (isError) Color.Red else Color.Transparent,
                letterSpacing = TextUnit(1f, TextUnitType.Sp),
                maxLines = 1,
                style = HatTypography.Regular10
            )
        }
    }
}