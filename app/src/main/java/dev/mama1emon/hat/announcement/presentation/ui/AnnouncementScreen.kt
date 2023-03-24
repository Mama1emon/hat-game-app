package dev.mama1emon.hat.announcement.presentation.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import dev.mama1emon.hat.R
import dev.mama1emon.hat.ds.theme.*

/**
 * @author Andrew Khokhlov on 21/03/2023
 */
@Composable
fun AnnouncementScreen(
    @StringRes titleResId: Int,
    @DrawableRes imageResId: Int,
    description: AnnotatedString,
    buttonText: String,
    onButtonClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.radialGradient(LightInDarkRadialGradient)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = titleResId),
            color = White,
            textAlign = TextAlign.Center,
            maxLines = 1,
            style = HatTypography.Regular42
        )
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = null,
            alignment = Alignment.Center
        )
        Text(
            text = description,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(R.dimen.padding46)),
            textAlign = TextAlign.Center,
            color = White,
            style = HatTypography.Regular16
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding40)))
        SuggestionButton(text = buttonText, onClick = onButtonClick)
    }
}

@Composable
private fun SuggestionButton(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .background(
                brush = Brush.horizontalGradient(GoldenLinearGradient),
                shape = RoundedCornerShape(size = dimensionResource(id = R.dimen.radius16))
            )
            .clip(shape = RoundedCornerShape(size = dimensionResource(id = R.dimen.radius16)))
            .clickable(onClick = onClick)
    ) {
        Text(
            text = text,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(
                    horizontal = dimensionResource(id = R.dimen.padding16),
                    vertical = dimensionResource(id = R.dimen.padding8)
                ),
            color = SpanishRoast,
            maxLines = 1,
            style = HatTypography.Medium18
        )
    }
}

