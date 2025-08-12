package dev.filinhat.bikecalc.feature.development.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import bikecalcmp.feature.development.generated.resources.Res
import bikecalcmp.feature.development.generated.resources.add_chainring
import bikecalcmp.feature.development.generated.resources.axis_x_rear_teeth
import bikecalcmp.feature.development.generated.resources.axis_y_development_m
import bikecalcmp.feature.development.generated.resources.axis_y_ratio
import bikecalcmp.feature.development.generated.resources.cassette_hint
import bikecalcmp.feature.development.generated.resources.front_chainring_n_hint
import bikecalcmp.feature.development.generated.resources.front_chainrings_title
import bikecalcmp.feature.development.generated.resources.legend_chainring_format
import bikecalcmp.feature.development.generated.resources.legend_title
import bikecalcmp.feature.development.generated.resources.new_calculation
import bikecalcmp.feature.development.generated.resources.ratio_title
import bikecalcmp.feature.development.generated.resources.remove_chainring
import bikecalcmp.feature.development.generated.resources.results_title
import bikecalcmp.feature.development.generated.resources.rim_diameter_mm
import bikecalcmp.feature.development.generated.resources.tire_width_mm
import dev.filinhat.bikecalc.core.common.util.toBikeDouble
import dev.filinhat.bikecalc.core.model.development.DevelopmentCalcParams
import dev.filinhat.bikecalc.core.model.development.DevelopmentCalcResult
import dev.filinhat.bikecalc.feature.development.component.DevelopmentCharts
import dev.filinhat.bikecalc.feature.development.component.InputCard
import dev.filinhat.bikecalc.feature.development.state.DevelopmentCalcAction
import dev.filinhat.bikecalc.feature.development.state.DevelopmentCalcState
import dev.filinhat.bikecalc.feature.development.viewmodel.DevelopmentCalculatorViewModel
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement as RowArrangement

/**
 * Экран для расчёта развития метража (метры на оборот педалей).
 * Позволяет ввести параметры и получить результат.
 *
 * @param viewModel ViewModel для экрана развития метража
 */
@Composable
fun DevelopmentCalculatorScreenRoot(
    viewModel: DevelopmentCalculatorViewModel,
    keyboardController: SoftwareKeyboardController?,
    focusManager: FocusManager,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    DevelopmentCalculatorScreen(
        uiState = uiState,
        onAction = viewModel::onAction,
        keyboardController = keyboardController,
        focusManager = focusManager,
    )
}

@Composable
private fun DevelopmentCalculatorScreen(
    uiState: DevelopmentCalcState,
    onAction: (DevelopmentCalcAction) -> Unit,
    modifier: Modifier = Modifier,
    keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current,
    focusManager: FocusManager? = LocalFocusManager.current,
) {
    var rimDiameter by remember { mutableStateOf("622") }
    var tireWidth by remember { mutableStateOf("57") }
    var frontTeethInputs by remember { mutableStateOf(listOf("32")) }
    var rearTeeth by remember { mutableStateOf("10,12,14,16,18,21,24,28,33,39,45,51") }

    Column(
        modifier =
            modifier
                .verticalScroll(rememberScrollState())
                .widthIn(max = 700.dp)
                .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        InputCard(
            value = rimDiameter,
            onValueChange = { rimDiameter = it },
            label = stringResource(Res.string.rim_diameter_mm),
            keyboardType = KeyboardType.Number,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.padding(8.dp))
        InputCard(
            value = tireWidth,
            onValueChange = { tireWidth = it },
            label = stringResource(Res.string.tire_width_mm),
            keyboardType = KeyboardType.Number,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.padding(8.dp))

        Text(
            text = stringResource(Res.string.front_chainrings_title),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.padding(4.dp))

        frontTeethInputs.forEachIndexed { index, value ->
            InputCard(
                value = value,
                onValueChange = { newValue ->
                    frontTeethInputs =
                        frontTeethInputs.mapIndexed { i, v -> if (i == index) newValue else v }
                },
                label = stringResource(Res.string.front_chainring_n_hint, index + 1),
                keyboardType = KeyboardType.Number,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.padding(6.dp))
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = RowArrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Button(
                onClick = {
                    if (frontTeethInputs.size < 3) frontTeethInputs = frontTeethInputs + ""
                },
                enabled = frontTeethInputs.size < 3,
            ) { Text(stringResource(Res.string.add_chainring)) }

            Button(
                onClick = {
                    if (frontTeethInputs.size > 1) frontTeethInputs = frontTeethInputs.dropLast(1)
                },
                enabled = frontTeethInputs.size > 1,
            ) { Text(stringResource(Res.string.remove_chainring)) }
        }
        Spacer(modifier = Modifier.padding(8.dp))
        InputCard(
            value = rearTeeth,
            onValueChange = { rearTeeth = it },
            label = stringResource(Res.string.cassette_hint),
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Button(onClick = {
            keyboardController?.hide()
            focusManager?.clearFocus()
            val frontList = frontTeethInputs.mapNotNull { it.trim().toIntOrNull() }.take(3)
            val rearList = rearTeeth.split(",").mapNotNull { it.trim().toIntOrNull() }
            onAction(
                DevelopmentCalcAction.OnCalculateDevelopment(
                    DevelopmentCalcParams(
                        rimDiameterMm = rimDiameter.toBikeDouble(),
                        tireWidthMm = tireWidth.toBikeDouble(),
                        frontTeethList = frontList,
                        rearTeethList = rearList,
                    ),
                ),
            )
        }) {
            Text(stringResource(Res.string.new_calculation))
        }
        Spacer(modifier = Modifier.padding(8.dp))

        if (uiState.result.isNotEmpty()) {
            DevelopmentCharts(
                results = uiState.result,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Preview
@Composable
private fun DevelopmentCalculatorScreenPreview() {
    DevelopmentCalculatorScreen(
        uiState =
            DevelopmentCalcState(
                result =
                    persistentListOf(
                        DevelopmentCalcResult(frontTeeth = 32, rearTeeth = 10, developmentMeters = 7.91),
                        DevelopmentCalcResult(frontTeeth = 32, rearTeeth = 12, developmentMeters = 6.59),
                        DevelopmentCalcResult(frontTeeth = 32, rearTeeth = 14, developmentMeters = 5.65),
                        DevelopmentCalcResult(frontTeeth = 32, rearTeeth = 16, developmentMeters = 4.94),
                        DevelopmentCalcResult(frontTeeth = 42, rearTeeth = 10, developmentMeters = 10.38),
                        DevelopmentCalcResult(frontTeeth = 42, rearTeeth = 12, developmentMeters = 8.65),
                        DevelopmentCalcResult(frontTeeth = 42, rearTeeth = 14, developmentMeters = 7.41),
                        DevelopmentCalcResult(frontTeeth = 42, rearTeeth = 16, developmentMeters = 6.49),
                    ),
            ),
        onAction = {},
    )
}
