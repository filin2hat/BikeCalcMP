package dev.filinhat.bikecalc

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "BikeCalcMP",
    ) {
        // TODO: Set up your Compose UI here
    }
}