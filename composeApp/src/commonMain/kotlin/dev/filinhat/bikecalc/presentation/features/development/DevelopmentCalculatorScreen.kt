package dev.filinhat.bikecalc.presentation.features.development

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.filinhat.bikecalc.domain.model.DevelopmentCalcParams
import dev.filinhat.bikecalc.presentation.features.development.state.DevelopmentCalcAction
import dev.filinhat.bikecalc.presentation.features.development.state.DevelopmentCalcState
import dev.filinhat.bikecalc.presentation.features.development.viewmodel.DevelopmentCalculatorViewModel
import dev.filinhat.bikecalc.presentation.util.formatValue
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Экран для расчёта развития метража (метры на оборот педалей).
 * Позволяет ввести параметры и получить результат.
 *
 * @param viewModel ViewModel для экрана развития метража
 */
@Composable
fun DevelopmentCalculatorScreenRoot(viewModel: DevelopmentCalculatorViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    DevelopmentCalculatorScreen(
        uiState = uiState,
        onAction = viewModel::onAction,
    )
}

@Composable
fun DevelopmentCalculatorScreen(
    uiState: DevelopmentCalcState,
    onAction: (DevelopmentCalcAction) -> Unit,
) {
    var rimDiameter by remember { mutableStateOf(622.0) }
    var tireWidth by remember { mutableStateOf(25.0) }
    var frontTeeth by remember { mutableStateOf("50") }
    var rearTeeth by remember { mutableStateOf("12,13,15,17,19,21,23,25,28") }

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Калькулятор развития метража", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.padding(8.dp))
        BasicTextField(
            value = rimDiameter.toString(),
            onValueChange = { it.toDoubleOrNull()?.let { v -> rimDiameter = v } },
            modifier = Modifier.fillMaxWidth(),
            decorationBox = { inner ->
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    tonalElevation = 1.dp,
                ) {
                    Column(Modifier.padding(8.dp)) {
                        Text("Диаметр обода (мм)")
                        inner()
                    }
                }
            },
        )
        Spacer(modifier = Modifier.padding(4.dp))
        BasicTextField(
            value = tireWidth.toString(),
            onValueChange = { it.toDoubleOrNull()?.let { v -> tireWidth = v } },
            modifier = Modifier.fillMaxWidth(),
            decorationBox = { inner ->
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    tonalElevation = 1.dp,
                ) {
                    Column(Modifier.padding(8.dp)) {
                        Text("Ширина шины (мм)")
                        inner()
                    }
                }
            },
        )
        Spacer(modifier = Modifier.padding(4.dp))
        BasicTextField(
            value = frontTeeth,
            onValueChange = { frontTeeth = it },
            modifier = Modifier.fillMaxWidth(),
            decorationBox = { inner ->
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    tonalElevation = 1.dp,
                ) {
                    Column(Modifier.padding(8.dp)) {
                        Text("Передняя звезда (через запятую, например: 50,34)")
                        inner()
                    }
                }
            },
        )
        Spacer(modifier = Modifier.padding(4.dp))
        BasicTextField(
            value = rearTeeth,
            onValueChange = { rearTeeth = it },
            modifier = Modifier.fillMaxWidth(),
            decorationBox = { inner ->
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    tonalElevation = 1.dp,
                ) {
                    Column(Modifier.padding(8.dp)) {
                        Text("Кассета (через запятую, например: 12,13,15,17,19,21,23,25,28)")
                        inner()
                    }
                }
            },
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Button(onClick = {
            val frontList = frontTeeth.split(",").mapNotNull { it.trim().toIntOrNull() }
            val rearList = rearTeeth.split(",").mapNotNull { it.trim().toIntOrNull() }
            onAction(
                DevelopmentCalcAction.OnCalculateDevelopment(
                    DevelopmentCalcParams(
                        rimDiameterMm = rimDiameter,
                        tireWidthMm = tireWidth,
                        frontTeethList = frontList,
                        rearTeethList = rearList,
                    ),
                ),
            )
        }) {
            Text("Рассчитать")
        }
        Spacer(modifier = Modifier.padding(8.dp))
        if (uiState.result.isNotEmpty()) {
            Text("Результаты:", style = MaterialTheme.typography.titleMedium)
            uiState.result.forEach {
                Text("${it.frontTeeth}/${it.rearTeeth}: ${formatValue(it.developmentMeters, 2)} м")
            }
        }
    }
}

@Preview
@Composable
private fun PreviewDevelopmentCalculatorScreen() {
    DevelopmentCalculatorScreen(
        uiState = DevelopmentCalcState(),
        onAction = {},
    )
}
