package dev.mama1emon.hat.ds.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import dev.mama1emon.hat.R
import dev.mama1emon.hat.ds.theme.CitrusZest
import dev.mama1emon.hat.ds.theme.White

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
