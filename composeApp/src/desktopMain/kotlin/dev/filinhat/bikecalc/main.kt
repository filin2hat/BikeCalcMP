@file:Suppress("ktlint:standard:filename")

package dev.filinhat.bikecalc

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import dev.filinhat.bikecalc.app.App
import dev.filinhat.bikecalc.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "BikeCalc Pro",
            state = rememberWindowState(width = 500.dp, height = 880.dp),
        ) {
            App()
        }
    }
}
