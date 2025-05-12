package dev.filinhat.bikecalc.presentation.screen.pressure

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import bikecalcmp.composeapp.generated.resources.Res
import bikecalcmp.composeapp.generated.resources.app_bar_title
import bikecalcmp.composeapp.generated.resources.bike_weight
import bikecalcmp.composeapp.generated.resources.dialog_text_chapter_four
import bikecalcmp.composeapp.generated.resources.dialog_text_chapter_one
import bikecalcmp.composeapp.generated.resources.dialog_text_chapter_three
import bikecalcmp.composeapp.generated.resources.dialog_text_chapter_two
import bikecalcmp.composeapp.generated.resources.dialog_text_end
import bikecalcmp.composeapp.generated.resources.dialog_title
import bikecalcmp.composeapp.generated.resources.kg
import bikecalcmp.composeapp.generated.resources.lbs
import bikecalcmp.composeapp.generated.resources.rider_weight
import bikecalcmp.composeapp.generated.resources.tire_size
import bikecalcmp.composeapp.generated.resources.wheel_size
import compose.icons.FeatherIcons
import compose.icons.feathericons.Info
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
import dev.filinhat.bikecalc.presentation.ui.kit.common.DropdownMenu
import dev.filinhat.bikecalc.presentation.ui.kit.common.InfoDialog
import dev.filinhat.bikecalc.presentation.ui.kit.pressure.CalculatePressureButton
import dev.filinhat.bikecalc.presentation.ui.kit.pressure.PressureCard
import dev.filinhat.bikecalc.presentation.ui.kit.pressure.TubeTypeChangeButton
import dev.filinhat.bikecalc.presentation.util.validateBikeWeight
import dev.filinhat.bikecalc.presentation.util.validateUserWeight
import kotlinx.collections.immutable.toPersistentList
import org.jetbrains.compose.resources.stringResource

/**
 * Экран для расчета давления велосипеда.
 *
 * @param viewModel [PressureCalculatorViewModel]
 */
@Composable
internal fun PressureCalculatorScreenRoot(viewModel: PressureCalculatorViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    PressureCalculatorScreen(
        uiState = uiState,
        onCalcPressure = { bikeWeight, riderWeight, wheelSize, tireSize, weightUnit ->
            viewModel.perform(
                UiEvent.OnCalcPressure(
                    bikeWeight,
                    riderWeight,
                    wheelSize,
                    tireSize,
                    weightUnit,
                ),
            )
        },
    )
}

@Composable
private fun PressureCalculatorScreen(
    uiState: UiState,
    modifier: Modifier = Modifier,
    onCalcPressure: (
        bikeWeight: Double,
        riderWeight: Double,
        wheelSize: WheelSize,
        tireSize: TireSize,
        weightUnit: WeightUnit,
    ) -> Unit = { _, _, _, _, _ -> },
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    // val context = LocalContext.current

    var selectedUnitWeight by rememberSaveable { mutableStateOf(WeightUnit.KG) }
    var riderWeight: String by rememberSaveable { mutableStateOf("") }
    var bikeWeight: String by rememberSaveable { mutableStateOf("") }
    var wheelSize: WheelSize? by rememberSaveable { mutableStateOf(null) }
    var tireSize: TireSize? by rememberSaveable { mutableStateOf(null) }
    var selectedTubeType by rememberSaveable { mutableStateOf(TubeType.TUBES) }

    var wrongRiderWeight by rememberSaveable { mutableStateOf(false) }
    var wrongBikeWeight by rememberSaveable { mutableStateOf(false) }

    var expandedTireSize by rememberSaveable { mutableStateOf(false) }
    var expandedCalcResult by rememberSaveable { mutableStateOf(false) }
    var openInfoDialog by remember { mutableStateOf(false) }

    when (uiState) {
        UiState.Loading -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier =
                    modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
            ) {
                CircularProgressIndicator()
            }
        }

        is UiState.Error -> {
//
        }

        is UiState.Success -> {
            if (openInfoDialog) {
                InfoDialog(
                    onCloseDialog = { openInfoDialog = false },
                    dialogTitle = stringResource(Res.string.dialog_title),
                    dialogText =
                        stringResource(Res.string.dialog_text_chapter_one) + "\n\n" +
                            stringResource(Res.string.dialog_text_chapter_two) + "\n" +
                            stringResource(Res.string.dialog_text_chapter_three) + "\n" +
                            stringResource(Res.string.dialog_text_chapter_four) + "\n\n" +
                            stringResource(Res.string.dialog_text_end),
                    icon = FeatherIcons.Info,
                )
            }
            Column(modifier = modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(Res.string.app_bar_title),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary,
                    )

                    IconButton(
                        onClick = {
                            keyboardController?.hide()
                            focusManager.clearFocus()
                            openInfoDialog = true
                        },
                        colors =
                            IconButtonDefaults.iconButtonColors(
                                contentColor = MaterialTheme.colorScheme.primary,
                            ),
                        modifier = Modifier.size(48.dp),
                    ) {
                        Icon(
                            imageVector = FeatherIcons.Info,
                            contentDescription = null,
                        )
                    }
                }
                AnimatedVisibility(
                    visible = expandedCalcResult,
                    enter = expandVertically(),
                    exit = shrinkVertically(),
                    modifier = Modifier.padding(bottom = 18.dp),
                ) {
                    Column(
                        modifier =
                            Modifier
                                .padding(top = 4.dp)
                                .fillMaxWidth(),
                    ) {
                        PressureCard(
                            value =
                                when (selectedTubeType) {
                                    TubeType.TUBES -> uiState.result.tubesFront
                                    TubeType.TUBELESS -> uiState.result.tubelessFront
                                },
                            wheel = Wheel.Front,
                        )
                        Spacer(modifier = Modifier.size(18.dp))

                        PressureCard(
                            value =
                                when (selectedTubeType) {
                                    TubeType.TUBES -> uiState.result.tubesRear
                                    TubeType.TUBELESS -> uiState.result.tubelessRear
                                },
                            wheel = Wheel.Rear,
                        )
                    }
                }
                Row(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(bottom = 14.dp),
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
                    )
                    FilledIconButton(
                        modifier =
                            Modifier
                                .weight(0.20f)
                                .fillMaxWidth()
                                .height(78.dp)
                                .padding(top = 4.dp)
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
                        },
                    ) {
                        Text(
                            text =
                                if (selectedUnitWeight == WeightUnit.KG) {
                                    stringResource(Res.string.kg)
                                } else {
                                    stringResource(Res.string.lbs)
                                },
                            style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp),
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
                            .padding(
                                bottom = if (expandedTireSize) 18.dp else 22.dp,
                            ).fillMaxWidth(),
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
                                .padding(bottom = 22.dp)
                                .fillMaxWidth(),
                    )
                }
                TubeTypeChangeButton(
                    onClick = {
                        onCalcPressure(
                            bikeWeight.toDouble(),
                            riderWeight.toDouble(),
                            wheelSize ?: return@TubeTypeChangeButton,
                            tireSize ?: return@TubeTypeChangeButton,
                            selectedUnitWeight,
                        )
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    },
                    onTypeChange = { selectedTubeType = it },
                    enabled =
                        validateIfEmpty(
                            wrongRiderWeight,
                            wrongBikeWeight,
                            wheelSize,
                            tireSize,
                            riderWeight,
                            bikeWeight,
                        ),
                    selectedType = selectedTubeType,
                )

                CalculatePressureButton(
                    onClick = {
                        onCalcPressure(
                            bikeWeight.toDouble(),
                            riderWeight.toDouble(),
                            wheelSize ?: return@CalculatePressureButton,
                            tireSize ?: return@CalculatePressureButton,
                            selectedUnitWeight,
                        )
                        keyboardController?.hide()
                        focusManager.clearFocus()
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
    }
}

@Composable
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
