package dev.mama1emon.hat.ds.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable

private val colorPalette = darkColors(
    primary = SpanishRoast,
    primaryVariant = LostInSadness,
    surface = LostInSadness
)

@Composable
fun HatTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = colorPalette,
        shapes = Shapes(),
        content = content
    )
}
