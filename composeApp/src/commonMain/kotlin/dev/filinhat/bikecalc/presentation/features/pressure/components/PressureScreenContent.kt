package dev.filinhat.bikecalc.presentation.features.pressure.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bikecalcmp.composeapp.generated.resources.Res
import bikecalcmp.composeapp.generated.resources.bike_weight
import bikecalcmp.composeapp.generated.resources.kg
import bikecalcmp.composeapp.generated.resources.lbs
import bikecalcmp.composeapp.generated.resources.rider_weight
import bikecalcmp.composeapp.generated.resources.tire_size
import bikecalcmp.composeapp.generated.resources.wheel_size
import dev.filinhat.bikecalc.domain.enums.tire.TireSize
import dev.filinhat.bikecalc.domain.enums.tire.TireSize20Inches
import dev.filinhat.bikecalc.domain.enums.tire.TireSize20InchesBMX
import dev.filinhat.bikecalc.domain.enums.tire.TireSize24Inches
import dev.filinhat.bikecalc.domain.enums.tire.TireSize26Inches
import dev.filinhat.bikecalc.domain.enums.tire.TireSize275Inches
import dev.filinhat.bikecalc.domain.enums.tire.TireSize28Inches
import dev.filinhat.bikecalc.domain.enums.tire.TireSize29Inches
import dev.filinhat.bikecalc.domain.enums.tube.TubeType
import dev.filinhat.bikecalc.domain.enums.unit.WeightUnit
import dev.filinhat.bikecalc.domain.enums.wheel.Wheel
import dev.filinhat.bikecalc.domain.enums.wheel.WheelSize
import dev.filinhat.bikecalc.domain.model.PressureCalcResult
import dev.filinhat.bikecalc.presentation.features.pressure.state.PressureCalcAction
import dev.filinhat.bikecalc.presentation.features.pressure.state.PressureCalcState
import dev.filinhat.bikecalc.presentation.kit.DropdownMenu
import dev.filinhat.bikecalc.presentation.theme.BikeCalcTheme
import dev.filinhat.bikecalc.presentation.util.toBikeDouble
import dev.filinhat.bikecalc.presentation.util.validateBikeWeight
import dev.filinhat.bikecalc.presentation.util.validateUserWeight
import kotlinx.collections.immutable.toPersistentList
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Комонент экрана расчета давления.
 */
@Composable
fun PressureScreenContent(
    uiState: PressureCalcState,
    onAction: (PressureCalcAction) -> Unit,
    modifier: Modifier = Modifier,
    keyboardController: SoftwareKeyboardController? = null,
    focusManager: FocusManager? = null,
) {
    var selectedUnitWeight by rememberSaveable { mutableStateOf(WeightUnit.KG) }
    var riderWeight: String by rememberSaveable { mutableStateOf("") }
    var bikeWeight: String by rememberSaveable { mutableStateOf("") }
    var wheelSize: WheelSize? by rememberSaveable { mutableStateOf(null) }
    var tireSize: TireSize? by rememberSaveable { mutableStateOf(null) }

    var wrongRiderWeight by rememberSaveable { mutableStateOf(false) }
    var wrongBikeWeight by rememberSaveable { mutableStateOf(false) }

    var expandedTireSize by rememberSaveable { mutableStateOf(false) }
    var expandedCalcResult by rememberSaveable { mutableStateOf(false) }

    val haptic = LocalHapticFeedback.current

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
    ) {
        AnimatedVisibility(
            visible = expandedCalcResult,
            enter =
                fadeIn(animationSpec = tween(durationMillis = 150)) +
                    slideInVertically(animationSpec = tween(durationMillis = 150)),
            exit =
                fadeOut(animationSpec = tween(durationMillis = 150)) +
                    slideOutVertically(animationSpec = tween(durationMillis = 150)),
            modifier = Modifier.padding(bottom = 12.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                PressureCalcCard(
                    value = uiState.result.frontPressure,
                    wheel = Wheel.Front,
                )

                Spacer(modifier = Modifier.size(14.dp))

                PressureCalcCard(
                    value = uiState.result.rearPressure,
                    wheel = Wheel.Rear,
                )
            }
        }
        Row(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OutlinedTextField(
                value = riderWeight,
                onValueChange = {
                    riderWeight =
                        if (it.startsWith("0")) {
                            it.trimStart('0')
                        } else {
                            it
                        }
                    wrongRiderWeight = !validateUserWeight(riderWeight)
                    expandedCalcResult = false
                },
                label = {
                    Text(
                        text = stringResource(Res.string.rider_weight),
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                modifier =
                    Modifier
                        .weight(0.5f)
                        .fillMaxWidth(),
                isError = wrongRiderWeight,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                textStyle = MaterialTheme.typography.displaySmall,
                shape = MaterialTheme.shapes.medium,
                colors =
                    OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.background,
                        unfocusedContainerColor = MaterialTheme.colorScheme.background,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                        focusedTextColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.inversePrimary,
                    ),
                singleLine = true,
            )

            OutlinedTextField(
                value = bikeWeight,
                onValueChange = {
                    bikeWeight =
                        if (it.startsWith("0")) {
                            it.trimStart('0')
                        } else {
                            it
                        }
                    wrongBikeWeight = !validateBikeWeight(bikeWeight)
                    expandedCalcResult = false
                },
                label = {
                    Text(
                        text = stringResource(Res.string.bike_weight),
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                modifier =
                    Modifier
                        .weight(0.5f)
                        .fillMaxWidth(),
                isError = wrongBikeWeight,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                textStyle = MaterialTheme.typography.displaySmall,
                shape = MaterialTheme.shapes.medium,
                colors =
                    OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.background,
                        unfocusedContainerColor = MaterialTheme.colorScheme.background,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                        focusedTextColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.inversePrimary,
                    ),
                singleLine = true,
            )

            FilledIconButton(
                modifier =
                    Modifier
                        .weight(0.20f)
                        .fillMaxWidth()
                        .height(84.dp)
                        .padding(top = 8.dp)
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.inversePrimary,
                            shape = MaterialTheme.shapes.medium,
                        ),
                shape = MaterialTheme.shapes.medium,
                onClick = {
                    expandedCalcResult = false
                    selectedUnitWeight =
                        when (selectedUnitWeight) {
                            WeightUnit.KG -> {
                                WeightUnit.LBS
                            }

                            WeightUnit.LBS -> {
                                WeightUnit.KG
                            }
                        }
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                },
            ) {
                Text(
                    text =
                        if (selectedUnitWeight == WeightUnit.KG) {
                            stringResource(Res.string.kg)
                        } else {
                            stringResource(Res.string.lbs)
                        },
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp),
                )
            }
        }

        DropdownMenu(
            onItemSelect = {
                if (it != wheelSize) {
                    tireSize = null
                    expandedTireSize = false
                    expandedCalcResult = false
                }
                wheelSize = it
                expandedTireSize = true
            },
            label = stringResource(Res.string.wheel_size),
            items = WheelSize.entries.toPersistentList(),
            value = wheelSize,
            itemLabel = { it?.nameSize },
            modifier =
                Modifier
                    .padding(bottom = if (expandedTireSize) 10.dp else 16.dp)
                    .fillMaxWidth(),
        )

        AnimatedVisibility(
            visible = expandedTireSize,
            enter = expandVertically(),
            exit = shrinkVertically(),
        ) {
            DropdownMenu(
                onItemSelect = {
                    tireSize = it
                    expandedCalcResult = false
                },
                label = stringResource(Res.string.tire_size),
                items =
                    when (wheelSize) {
                        WheelSize.Inches20BMX -> TireSize20InchesBMX.entries.toPersistentList()
                        WheelSize.Inches20 -> TireSize20Inches.entries.toPersistentList()
                        WheelSize.Inches24 -> TireSize24Inches.entries.toPersistentList()
                        WheelSize.Inches26 -> TireSize26Inches.entries.toPersistentList()
                        WheelSize.Inches275 -> TireSize275Inches.entries.toPersistentList()
                        WheelSize.Inches28 -> TireSize28Inches.entries.toPersistentList()
                        WheelSize.Inches29 -> TireSize29Inches.entries.toPersistentList()
                        else -> null
                    },
                value = tireSize,
                itemLabel = { it?.nameSize },
                modifier =
                    Modifier
                        .padding(bottom = 16.dp)
                        .fillMaxWidth(),
            )
        }

        TubeTypeChangeButton(
            onClick = { tubeType ->
                onAction(PressureCalcAction.OnTubeTypeChanged(tubeType))
                keyboardController?.hide()
                focusManager?.clearFocus()
            },
            enabled =
                validateIfEmpty(
                    wrongRiderWeight,
                    wrongBikeWeight,
                    wheelSize,
                    tireSize,
                    riderWeight,
                    bikeWeight,
                ),
            selectedType = uiState.selectedTubeType,
        )

        CalculatePressureButton(
            onClick = {
                onAction(
                    PressureCalcAction.OnCalcPressure(
                        riderWeight = riderWeight.toBikeDouble(),
                        bikeWeight = bikeWeight.toBikeDouble(),
                        wheelSize = wheelSize ?: return@CalculatePressureButton,
                        tireSize = tireSize ?: return@CalculatePressureButton,
                        weightUnit = selectedUnitWeight,
                        selectedTubeType = uiState.selectedTubeType,
                    ),
                )
                keyboardController?.hide()
                focusManager?.clearFocus()
                expandedCalcResult = true
            },
            enabled =
                validateIfEmpty(
                    wrongRiderWeight,
                    wrongBikeWeight,
                    wheelSize,
                    tireSize,
                    riderWeight,
                    bikeWeight,
                ),
        )
    }
}

private fun validateIfEmpty(
    wrongRiderWeight: Boolean,
    wrongBikeWeight: Boolean,
    wheelSize: WheelSize?,
    tireSize: TireSize?,
    riderWeight: String,
    bikeWeight: String,
) = !wrongRiderWeight &&
    !wrongBikeWeight &&
    wheelSize != null &&
    tireSize != null &&
    riderWeight.isNotEmpty() &&
    bikeWeight.isNotEmpty()

@Preview
@Composable
private fun PressureScreenContentPreview() {
    BikeCalcTheme {
        PressureScreenContent(
            uiState =
                PressureCalcState(
                    result =
                        PressureCalcResult(
                            frontPressure = 2.5,
                            rearPressure = 2.8,
                        ),
                    selectedTubeType = TubeType.TUBELESS,
                ),
            onAction = {},
            keyboardController = null,
            focusManager = null,
        )
    }
}
