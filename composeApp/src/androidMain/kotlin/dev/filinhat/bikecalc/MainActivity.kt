package dev.filinhat.bikecalc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dev.filinhat.bikecalc.presentation.screen.main.MainScreen
import dev.filinhat.bikecalc.presentation.ui.theme.BikeCalcTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BikeCalcTheme {
                MainScreen()
            }
        }
    }
}