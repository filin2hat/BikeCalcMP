package dev.filinhat.bikecalc.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import bikecalcmp.composeapp.generated.resources.Montserrat_Bold
import bikecalcmp.composeapp.generated.resources.Montserrat_Light
import bikecalcmp.composeapp.generated.resources.Montserrat_Medium
import bikecalcmp.composeapp.generated.resources.Montserrat_Regular
import bikecalcmp.composeapp.generated.resources.Montserrat_Thin
import bikecalcmp.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Font

@OptIn(ExperimentalResourceApi::class)
@Composable
fun MontserratFontFamily() =
    FontFamily(
        Font(Res.font.Montserrat_Thin, FontWeight.Thin, FontStyle.Normal),
        Font(Res.font.Montserrat_Light, FontWeight.Light, FontStyle.Normal),
        Font(Res.font.Montserrat_Regular, FontWeight.Normal, FontStyle.Normal),
        Font(Res.font.Montserrat_Medium, FontWeight.Medium, FontStyle.Normal),
        Font(Res.font.Montserrat_Bold, FontWeight.Bold, FontStyle.Normal),
    )

@Composable
fun MontserratTypography() =
    Typography().run {
        val fontFamily = MontserratFontFamily()
        copy(
            displayLarge = displayLarge.copy(fontFamily = fontFamily),
            displayMedium = displayMedium.copy(fontFamily = fontFamily),
            displaySmall = displaySmall.copy(fontFamily = fontFamily),
            headlineLarge = headlineLarge.copy(fontFamily = fontFamily),
            headlineMedium = headlineMedium.copy(fontFamily = fontFamily),
            headlineSmall = headlineSmall.copy(fontFamily = fontFamily),
            titleLarge = titleLarge.copy(fontFamily = fontFamily),
            titleMedium = titleMedium.copy(fontFamily = fontFamily),
            titleSmall = titleSmall.copy(fontFamily = fontFamily),
            bodyLarge = bodyLarge.copy(fontFamily = fontFamily),
            bodyMedium = bodyMedium.copy(fontFamily = fontFamily),
            bodySmall = bodySmall.copy(fontFamily = fontFamily),
            labelLarge = labelLarge.copy(fontFamily = fontFamily),
            labelMedium = labelMedium.copy(fontFamily = fontFamily),
            labelSmall = labelSmall.copy(fontFamily = fontFamily),
        )
    }
