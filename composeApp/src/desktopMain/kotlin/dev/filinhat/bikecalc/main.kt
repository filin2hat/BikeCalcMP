@file:Suppress("ktlint:standard:filename")

package dev.filinhat.bikecalc

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.filinhat.bikecalc.presentation.screen.main.MainScreen
import dev.filinhat.bikecalc.presentation.ui.theme.BikeCalcTheme

fun main() =
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "BikeCalcMP",
        ) {
            BikeCalcTheme { MainScreen() }
        }
    }
