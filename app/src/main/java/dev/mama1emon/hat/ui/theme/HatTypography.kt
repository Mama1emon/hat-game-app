package dev.mama1emon.hat.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.sp
import dev.mama1emon.hat.R

@OptIn(ExperimentalUnitApi::class)
object HatTypography {
    private val ManropeFontFamily = FontFamily(
        Font(R.font.manrope_regular, FontWeight.Normal),
        Font(R.font.manrope_semibold, FontWeight.SemiBold)
    )

    val Regular10 = TextStyle(
        fontFamily = ManropeFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        lineHeight = TextUnit(value = 16f, type = TextUnitType.Sp)
    )

    val Regular12 = TextStyle(
        fontFamily = ManropeFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = TextUnit(value = 16f, type = TextUnitType.Sp)
    )

    val Regular14 = TextStyle(
        fontFamily = ManropeFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = TextUnit(value = 16f, type = TextUnitType.Sp)
    )

    val Regular16 = TextStyle(
        fontFamily = ManropeFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = TextUnit(value = 16f, type = TextUnitType.Sp)
    )

    val Regular18 = TextStyle(
        fontFamily = ManropeFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = TextUnit(value = 16f, type = TextUnitType.Sp)
    )

    val Regular20 = TextStyle(
        fontFamily = ManropeFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        lineHeight = TextUnit(value = 16f, type = TextUnitType.Sp)
    )

    val Medium18 = TextStyle(
        fontFamily = ManropeFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = TextUnit(value = 16f, type = TextUnitType.Sp)
    )

    val Medium24 = TextStyle(
        fontFamily = ManropeFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        lineHeight = TextUnit(value = 16f, type = TextUnitType.Sp)
    )

    val Regular42 = TextStyle(
        fontFamily = ManropeFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 42.sp,
        lineHeight = TextUnit(value = 16f, type = TextUnitType.Sp)
    )
}
