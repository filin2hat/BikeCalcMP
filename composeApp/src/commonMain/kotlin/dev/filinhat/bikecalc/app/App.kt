package dev.filinhat.bikecalc.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import dev.filinhat.bikecalc.app.navigation.BikeCalcNavigation
import dev.filinhat.bikecalc.presentation.theme.BikeCalcTheme

/**
 * Точка входа в приложение
 */
@Composable
fun App() {
    BikeCalcTheme {
        NoFontScalingContent {
            BikeCalcNavigation()
        }
    }
}

@Composable
fun NoFontScalingContent(content: @Composable () -> Unit) {
    val currentDensity = LocalDensity.current
    CompositionLocalProvider(
        LocalDensity provides Density(currentDensity.density, fontScale = 1.0f),
    ) {
        content()
    }
}
