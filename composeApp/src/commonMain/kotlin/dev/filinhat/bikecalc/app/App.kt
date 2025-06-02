package dev.filinhat.bikecalc.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.filinhat.bikecalc.app.navigation.BikeCalcNavigation
import dev.filinhat.bikecalc.presentation.ui.theme.BikeCalcTheme

@Composable
fun App(modifier: Modifier = Modifier) {
    BikeCalcTheme {
        BikeCalcNavigation()
    }
}
