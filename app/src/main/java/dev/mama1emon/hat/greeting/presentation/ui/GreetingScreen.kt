package dev.mama1emon.hat.greeting.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.style.TextDecoration
import dev.mama1emon.hat.R
import dev.mama1emon.hat.ds.components.OutlinedButton
import dev.mama1emon.hat.ds.theme.*

@OptIn(ExperimentalTextApi::class)
@Composable
fun GreetingScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(QuillTip, LostInSadness)
                )
            ),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.hat),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = dimensionResource(R.dimen.padding18)),
            style = HatTypography.Lobster64.copy(
                brush = Brush.linearGradient(
                    colors = listOf(CitrusZest, WarmPurple, RoyalHunterBlue)
                )
            )
        )

        Image(
            painter = painterResource(R.drawable.ill_hat_with_magic_wand),
            contentDescription = null,
            modifier = Modifier
                .padding(
                    start = dimensionResource(R.dimen.padding65),
                    bottom = dimensionResource(R.dimen.padding36)
                )
                .size(dimensionResource(R.dimen.size270))
        )

        OutlinedButton(
            onClick = { /*TODO("HAT-8")*/ },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = dimensionResource(R.dimen.padding20))
                .size(
                    width = dimensionResource(R.dimen.size220),
                    height = dimensionResource(R.dimen.size54)
                ),
            text = stringResource(R.string.play),
            style = HatTypography.Regular24
        )

        Text(
            text = stringResource(R.string.rules_of_the_game),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = { /*TODO("HAT-8")*/ },
                ),
            color = White,
            style = HatTypography.Regular16.copy(textDecoration = TextDecoration.Underline)
        )
    }
}
