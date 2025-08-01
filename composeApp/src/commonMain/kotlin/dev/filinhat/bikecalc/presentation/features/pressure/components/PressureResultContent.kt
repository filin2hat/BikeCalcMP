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
                    modifier = modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    items(
                        items = savedResults,
                        key = { result ->
                            // Создаем уникальный ключ из данных объекта
                            "${result.pressureFront}-${result.pressureRear}-${result.riderWeight}-${result.bikeWeight}-${result.wheelSize}-${result.tireSize}"
                        },
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
