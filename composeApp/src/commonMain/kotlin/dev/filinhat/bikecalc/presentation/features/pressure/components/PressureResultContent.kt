package dev.filinhat.bikecalc.presentation.features.pressure.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import bikecalcmp.composeapp.generated.resources.Res
import bikecalcmp.composeapp.generated.resources.no_results
import dev.filinhat.bikecalc.domain.model.SavedPressureCalcResult
import dev.filinhat.bikecalc.presentation.features.pressure.state.PressureCalcAction
import kotlinx.collections.immutable.ImmutableList
import org.jetbrains.compose.resources.stringResource

/**
 * Компонент отображения сохраненных результатов расчета давления.
 */
@Composable
fun PressureResultContent(
    onAction: (PressureCalcAction) -> Unit,
    savedResults: ImmutableList<SavedPressureCalcResult>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        when (savedResults.isNotEmpty()) {
            true -> {
                DeleteResultsButton(
                    onClick = {
                        onAction(
                            PressureCalcAction.OnDeleteAllResults,
                        )
                    },
                )
                Column(
                    modifier =
                        modifier
                            .fillMaxSize()
                            .padding(vertical = 16.dp)
                            .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    savedResults.onEach { savedResults ->
                        PressureResultCard(savedResults)
                    }
                }
            }

            false -> {
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = stringResource(Res.string.no_results),
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
            }
        }
    }
}
