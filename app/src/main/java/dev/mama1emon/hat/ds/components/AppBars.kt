package dev.mama1emon.hat.ds.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import dev.mama1emon.hat.R
import dev.mama1emon.hat.ds.theme.HatTypography
import dev.mama1emon.hat.ds.theme.SpanishRoast
import dev.mama1emon.hat.ds.theme.White

@Composable
fun AppBar(@StringRes titleId: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = SpanishRoast)
            .padding(
                horizontal = dimensionResource(id = R.dimen.padding16),
                vertical = dimensionResource(id = R.dimen.padding12)
            )
    ) {
        Text(
            text = stringResource(titleId),
            modifier = Modifier.align(Alignment.Center),
            color = White,
            maxLines = 1,
            style = HatTypography.Regular16
        )
    }
}