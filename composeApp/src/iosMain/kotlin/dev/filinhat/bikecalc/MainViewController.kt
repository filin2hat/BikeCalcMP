package dev.filinhat.bikecalc

import androidx.compose.ui.window.ComposeUIViewController
import dev.filinhat.bikecalc.app.App
import dev.filinhat.bikecalc.di.initKoin

fun MainViewController() {
    ComposeUIViewController(
        configure = { initKoin() },
    ) {
        App()
    }
}
