package dev.filinhat.bikecalc.feature.development.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import bikecalcmp.feature.development.generated.resources.cassette_hint
import bikecalcmp.feature.development.generated.resources.front_chainring_hint
import bikecalcmp.feature.development.generated.resources.new_calculation
import bikecalcmp.feature.development.generated.resources.results_title
import bikecalcmp.feature.development.generated.resources.rim_diameter_mm
import bikecalcmp.feature.development.generated.resources.tire_width_mm
import com.patrykandpatrick.vico.multiplatform.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.multiplatform.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.multiplatform.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.multiplatform.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.multiplatform.cartesian.data.lineSeries
import com.patrykandpatrick.vico.multiplatform.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.multiplatform.cartesian.rememberCartesianChart
import dev.filinhat.bikecalc.core.common.util.formatDoubleToString
import dev.filinhat.bikecalc.core.common.util.toBikeDouble
import dev.filinhat.bikecalc.core.model.development.DevelopmentCalcParams
import dev.filinhat.bikecalc.feature.development.component.InputCard
import dev.filinhat.bikecalc.feature.development.state.DevelopmentCalcAction
import dev.filinhat.bikecalc.feature.development.state.DevelopmentCalcState
import dev.filinhat.bikecalc.feature.development.viewmodel.DevelopmentCalculatorViewModel
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

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
    var tireWidth by remember { mutableStateOf("25") }
    var frontTeeth by remember { mutableStateOf("50") }
    var rearTeeth by remember { mutableStateOf("12,13,15,17,19,21,23,25,28") }

    Column(
        modifier = modifier.padding(16.dp),
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
        InputCard(
            value = frontTeeth,
            onValueChange = { frontTeeth = it },
            label = stringResource(Res.string.front_chainring_hint),
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.padding(8.dp))
        InputCard(
            value = rearTeeth,
            onValueChange = { rearTeeth = it },
            label = stringResource(Res.string.cassette_hint),
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Button(onClick = {
            val frontList = frontTeeth.split(",").mapNotNull { it.trim().toIntOrNull() }
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
            Text(stringResource(Res.string.results_title), style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            val modelProducer = remember { CartesianChartModelProducer() }
            val rearTeethList =
                remember(uiState.result) {
                    uiState.result
                        .map { it.rearTeeth }
                        .distinct()
                        .sorted()
                }

            LaunchedEffect(uiState.result) {
                val series =
                    rearTeethList.map { rearTeeth ->
                        uiState.result
                            .find { it.rearTeeth == rearTeeth }
                            ?.developmentMeters
                            ?.toFloat()
                            ?: 0f
                    }
                modelProducer.runTransaction {
                    lineSeries { series(*series.toTypedArray()) }
                }
            }

            CartesianChartHost(
                chart =
                    rememberCartesianChart(
                        rememberLineCartesianLayer(),
                        startAxis = VerticalAxis.rememberStart(),
                        bottomAxis =
                            HorizontalAxis.rememberBottom(
                                valueFormatter = { _, value, _ ->
                                    rearTeethList.getOrNull(value.toInt())?.toString() ?: ""
                                },
                            ),
                    ),
                modelProducer = modelProducer,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(300.dp),
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Text results
            uiState.result.forEach {
                Text(
                    "${it.frontTeeth}/${it.rearTeeth}: ${
                        formatDoubleToString(
                            it.developmentMeters,
                            2,
                        )
                    } м",
                )
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
