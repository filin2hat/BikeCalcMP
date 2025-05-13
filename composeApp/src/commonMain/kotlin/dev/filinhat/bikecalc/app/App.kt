package dev.filinhat.bikecalc.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.filinhat.bikecalc.app.navigation.BikeCalcNavigation
import dev.filinhat.bikecalc.presentation.ui.theme.BikeCalcTheme

@Composable
fun App(modifier: Modifier = Modifier) {
    BikeCalcTheme {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxSize(),
            content = { innerPadding ->
                Column(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                            .padding(top = innerPadding.calculateTopPadding())
                            .padding(bottom = innerPadding.calculateBottomPadding())
                            .padding(horizontal = 16.dp),
                ) {
                    BikeCalcNavigation()
                }
            },
        )
    }
}
