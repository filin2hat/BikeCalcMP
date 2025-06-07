package dev.filinhat.bikecalc.app

import androidx.compose.runtime.Composable
import dev.filinhat.bikecalc.app.navigation.BikeCalcNavigation
import dev.filinhat.bikecalc.presentation.theme.BikeCalcTheme

/**
 * Точка входа в приложение
 */
@Composable
fun App() {
    BikeCalcTheme {
        BikeCalcNavigation()
    }
}
