package dev.filinhat.bikecalc.presentation.features.pressure.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import dev.filinhat.bikecalc.presentation.theme.BikeCalcTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

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
                Spacer(modifier = Modifier.height(8.dp))
                DeleteResultsButton(
                    onClick = {
                        onAction(
                            PressureCalcAction.OnDeleteAllResults,
                        )
                    },
                )

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    items(
                        items = savedResults,
                        key = { result -> result.id },
                    ) { result ->
                        PressureResultCard(result)
                    }
                }
            }

            false -> {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
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

@Preview
@Composable
private fun PressureResultContentPreview() {
    BikeCalcTheme {
        PressureResultContent(
            onAction = {},
            savedResults =
                persistentListOf(
                    SavedPressureCalcResult(
                        id = 1,
                        pressureFront = 2.5,
                        pressureRear = 2.8,
                        riderWeight = 70.0,
                        bikeWeight = 10.0,
                        wheelSize = "29",
                        tireSize = "2.25",
                    ),
                    SavedPressureCalcResult(
                        id = 2,
                        pressureFront = 2.3,
                        pressureRear = 2.6,
                        riderWeight = 65.0,
                        bikeWeight = 9.5,
                        wheelSize = "27.5",
                        tireSize = "2.1",
                    ),
                ),
        )
    }
}

@Preview
@Composable
private fun PressureResultContentEmptyPreview() {
    BikeCalcTheme {
        PressureResultContent(
            onAction = {},
            savedResults = persistentListOf(),
        )
    }
}
