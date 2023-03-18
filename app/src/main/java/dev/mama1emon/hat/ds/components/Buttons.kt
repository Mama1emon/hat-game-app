package dev.mama1emon.hat.ds.components

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import dev.mama1emon.hat.R
import dev.mama1emon.hat.ds.theme.*

@Composable
fun OutlinedButton(onClick: () -> Unit, modifier: Modifier, text: String, style: TextStyle) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(corner = CornerSize(dimensionResource(R.dimen.radius16))),
        border = BorderStroke(
            width = dimensionResource(R.dimen.size1),
            brush = SolidColor(value = CitrusZest)
        )
    ) {
        Text(text = text, color = White, style = style)
    }
}

@Composable
fun LargeButton(
    @StringRes textId: Int,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clickable(enabled = enabled, onClick = onClick)
            .fillMaxWidth()
            .background(
                brush = if (enabled) {
                    Brush.horizontalGradient(GoldenLinearGradient)
                } else {
                    SolidColor(BlackEel)
                },
                shape = RoundedCornerShape(size = dimensionResource(id = R.dimen.radius16))
            ),
    ) {
        Text(
            text = stringResource(id = textId),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(vertical = dimensionResource(id = R.dimen.padding14)),
            color = SpanishRoast,
            maxLines = 1,
            style = HatTypography.Medium24
        )
    }
}

@Composable
fun SmallButton(
    @StringRes textId: Int,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clickable(enabled = enabled, onClick = onClick)
            .background(
                brush = if (enabled) {
                    Brush.horizontalGradient(GoldenLinearGradient)
                } else {
                    SolidColor(BlackEel)
                },
                shape = RoundedCornerShape(size = dimensionResource(id = R.dimen.radius8))
            ),
    ) {
        Text(
            text = stringResource(id = textId),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(vertical = dimensionResource(id = R.dimen.padding5)),
            color = SpanishRoast,
            maxLines = 1,
            style = HatTypography.Regular12
        )
    }
}
