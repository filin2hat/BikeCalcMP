package dev.filinhat.bikecalc.presentation.screen.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.filinhat.bikecalc.presentation.screen.pressure.PressureCalculatorScreen

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize(),
        content = { innerPadding ->
            Column(
                modifier =
                    Modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxSize()
                        .padding(top = innerPadding.calculateTopPadding())
                        .padding(bottom = innerPadding.calculateBottomPadding())
                        .padding(horizontal = 16.dp),
            ) {
                PressureCalculatorScreen()
            }
        },
    )
}
