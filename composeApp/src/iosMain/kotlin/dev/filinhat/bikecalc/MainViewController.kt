package dev.filinhat.bikecalc

import androidx.compose.ui.window.ComposeUIViewController
import dev.filinhat.bikecalc.presentation.screen.main.MainScreen
import dev.filinhat.bikecalc.presentation.ui.theme.BikeCalcTheme

@Suppress("ktlint:standard:function-naming")
fun MainViewController() =
    ComposeUIViewController {
        BikeCalcTheme { MainScreen() }
    }
