package dev.filinhat.bikecalc.feature.development.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
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
import bikecalcmp.feature.development.generated.resources.cassette
import bikecalcmp.feature.development.generated.resources.cassette_hint
import bikecalcmp.feature.development.generated.resources.front_chainring_n_hint
import bikecalcmp.feature.development.generated.resources.front_chainrings_title
import bikecalcmp.feature.development.generated.resources.tire_width_mm
import bikecalcmp.feature.development.generated.resources.wheel_size
import compose.icons.LineAwesomeIcons
import compose.icons.lineawesomeicons.MinusSolid
import compose.icons.lineawesomeicons.PlusSolid
import dev.filinhat.bikecalc.core.common.util.toBikeDouble
import dev.filinhat.bikecalc.core.enums.wheel.WheelSize
import dev.filinhat.bikecalc.core.model.development.DevelopmentCalcParams
import dev.filinhat.bikecalc.designsystem.component.CalculatePressureButton
import dev.filinhat.bikecalc.designsystem.component.CompactNumericInputField
import dev.filinhat.bikecalc.feature.development.component.DevelopmentCharts
import dev.filinhat.bikecalc.feature.development.component.WheelSizeDropdown
import dev.filinhat.bikecalc.feature.development.state.DevelopmentCalcAction
import dev.filinhat.bikecalc.feature.development.state.DevelopmentCalcState
import dev.filinhat.bikecalc.feature.development.viewmodel.DevelopmentCalculatorViewModel
import org.jetbrains.compose.resources.stringResource
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
    var wheelSize by rememberSaveable { mutableStateOf(WheelSize.Inches29.name) }
    val selectedWheelSize =
        WheelSize.entries.firstOrNull { it.name == wheelSize } ?: WheelSize.Inches29
    var tireWidth by rememberSaveable { mutableStateOf("57") }
    var frontTeethInputs by rememberSaveable { mutableStateOf(listOf("32")) }
    var rearTeeth by rememberSaveable { mutableStateOf("10,12,14,16,18,21,24,28,33,39,45,51") }

    Column(
        modifier =
            modifier
                .verticalScroll(rememberScrollState())
                .widthIn(max = 700.dp)
                .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = RowArrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            WheelSizeDropdown(
                value = selectedWheelSize,
                onValueChange = { wheelSize = it.name },
                label = stringResource(Res.string.wheel_size),
                modifier =
                    Modifier
                        .weight(1f)
                        .fillMaxWidth(),
            )
            CompactNumericInputField(
                value = tireWidth,
                onValueChange = { tireWidth = it },
                label = stringResource(Res.string.tire_width_mm),
                keyboardType = KeyboardType.Number,
                modifier =
                    Modifier
                        .weight(1f)
                        .fillMaxWidth(),
            )
        }

        Spacer(modifier = Modifier.padding(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = RowArrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(Res.string.front_chainrings_title),
                style = MaterialTheme.typography.titleMedium,
                modifier =
                    Modifier
                        .weight(1f)
                        .fillMaxWidth(),
            )
            Row(
                modifier =
                    Modifier
                        .weight(0.3f)
                        .padding(bottom = 6.dp)
                        .fillMaxWidth(),
                horizontalArrangement = RowArrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    onClick = {
                        if (frontTeethInputs.size < 3) frontTeethInputs = frontTeethInputs + ""
                    },
                    enabled = frontTeethInputs.size < 3,
                    modifier =
                        Modifier
                            .height(32.dp)
                            .weight(1f),
                    colors =
                        IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                        ),
                ) {
                    Icon(
                        imageVector = LineAwesomeIcons.PlusSolid,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                    )
                }

                IconButton(
                    onClick = {
                        if (frontTeethInputs.size > 1) {
                            frontTeethInputs =
                                frontTeethInputs.dropLast(1)
                        }
                    },
                    enabled = frontTeethInputs.size > 1,
                    modifier =
                        Modifier
                            .height(32.dp)
                            .weight(1f),
                    colors =
                        IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                        ),
                ) {
                    Icon(
                        imageVector = LineAwesomeIcons.MinusSolid,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                    )
                }
            }
        }

        frontTeethInputs.forEachIndexed { index, value ->
            CompactNumericInputField(
                value = value,
                onValueChange = { newValue ->
                    frontTeethInputs =
                        frontTeethInputs.mapIndexed { i, v -> if (i == index) newValue else v }
                },
                label = stringResource(Res.string.front_chainring_n_hint, index + 1),
                keyboardType = KeyboardType.Number,
                modifier = Modifier.fillMaxWidth(),
            )
        }

        Spacer(modifier = Modifier.padding(8.dp))

        Text(
            text = stringResource(Res.string.cassette),
            style = MaterialTheme.typography.titleMedium,
            modifier =
                Modifier
                    .fillMaxWidth(),
        )

        Spacer(modifier = Modifier.padding(2.dp))

        CompactNumericInputField(
            value = rearTeeth,
            onValueChange = { rearTeeth = it },
            label = stringResource(Res.string.cassette_hint),
            allowMultipleValues = true,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.padding(8.dp))

        CalculatePressureButton(
            onClick = {
                keyboardController?.hide()
                focusManager?.clearFocus()
                val frontList = frontTeethInputs.mapNotNull { it.trim().toIntOrNull() }.take(3)
                val rearList = rearTeeth.split(",").mapNotNull { it.trim().toIntOrNull() }
                onAction(
                    DevelopmentCalcAction.OnCalculateDevelopment(
                        DevelopmentCalcParams(
                            rimDiameterMm = selectedWheelSize.etrtoMm.toDouble(),
                            tireWidthMm = tireWidth.toBikeDouble(),
                            frontTeethList = frontList,
                            rearTeethList = rearList,
                        ),
                    ),
                )
            },
            enabled = true,
        )
        Spacer(modifier = Modifier.padding(8.dp))

        AnimatedVisibility(
            visible = uiState.result.isNotEmpty(),
            enter =
                fadeIn(animationSpec = tween(durationMillis = 450)) +
                    slideInHorizontally(animationSpec = tween(durationMillis = 450)),
        ) {
            DevelopmentCharts(
                results = uiState.result,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
