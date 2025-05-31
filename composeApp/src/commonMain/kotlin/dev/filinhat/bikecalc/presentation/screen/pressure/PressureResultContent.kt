package dev.filinhat.bikecalc.presentation.screen.pressure

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import dev.filinhat.bikecalc.domain.model.SavedPressureCalcResult
import dev.filinhat.bikecalc.presentation.ui.kit.pressure.DeleteResultsButton
import dev.filinhat.bikecalc.presentation.ui.kit.pressure.PressureResultCard

@Composable
fun PressureResultContent(
    onAction: (PressureCalcAction) -> Unit,
    savedResults: List<SavedPressureCalcResult>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        DeleteResultsButton(
            onClick = {
                onAction(
                    PressureCalcAction.OnDeleteAllResults,
                )
            },
            enabled = savedResults.isNotEmpty(),
        )
        when (savedResults.isNotEmpty()) {
            true -> {
                savedResults.onEach { savedResults ->
                    PressureResultCard(savedResults)
                }
            }

            false -> {
                Text(
                    text = "No results",
                    color = White,
                )
            }
        }
    }
}
