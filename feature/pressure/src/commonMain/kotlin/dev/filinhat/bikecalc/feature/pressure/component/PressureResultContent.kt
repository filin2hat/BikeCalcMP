package dev.filinhat.bikecalc.feature.pressure.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import bikecalcmp.feature.pressure.generated.resources.Res
import bikecalcmp.feature.pressure.generated.resources.no_results
import dev.filinhat.bikecalc.core.model.SavedPressureCalcResult
import dev.filinhat.bikecalc.designsystem.theme.BikeCalcTheme
import dev.filinhat.bikecalc.feature.pressure.state.PressureCalcAction
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Компонент отображения сохраненных результатов расчета давления.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PressureResultContent(
    onAction: (PressureCalcAction) -> Unit,
    savedResults: ImmutableList<SavedPressureCalcResult>,
    modifier: Modifier = Modifier,
) {
    var selectedResultForDetails by remember { mutableStateOf<SavedPressureCalcResult?>(null) }

    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        AnimatedVisibility(
            visible = savedResults.isNotEmpty(),
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            Column {
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
                        PressureResultCard(
                            modifier = Modifier.animateItem(),
                            result = result,
                            onClick = {
                                selectedResultForDetails = result
                            },
                            onLongClick = { savedResult ->
                                onAction(
                                    PressureCalcAction.OnDeleteResult(savedResult.id),
                                )
                            },
                        )
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = savedResults.isEmpty(),
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
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

    selectedResultForDetails?.let { result ->
        DetailedInfoDialog(
            result = result,
            onDismissRequest = { selectedResultForDetails = null },
        )
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
