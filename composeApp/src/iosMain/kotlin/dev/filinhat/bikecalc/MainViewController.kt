package dev.filinhat.bikecalc

import androidx.compose.ui.window.ComposeUIViewController
import dev.filinhat.bikecalc.di.initKoin
import dev.filinhat.bikecalc.presentation.screen.main.MainScreen
import dev.filinhat.bikecalc.presentation.ui.theme.BikeCalcTheme

fun MainViewController() {
    ComposeUIViewController(
        configure = { initKoin() },
    ) {
        BikeCalcTheme { MainScreen() }
    }
}
