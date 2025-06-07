package dev.filinhat.bikecalc.app

import androidx.compose.runtime.Composable
import dev.filinhat.bikecalc.app.navigation.BikeCalcNavigation
import dev.filinhat.bikecalc.presentation.ui.theme.BikeCalcTheme

@Composable
fun App() {
    BikeCalcTheme {
        BikeCalcNavigation()
    }
}
