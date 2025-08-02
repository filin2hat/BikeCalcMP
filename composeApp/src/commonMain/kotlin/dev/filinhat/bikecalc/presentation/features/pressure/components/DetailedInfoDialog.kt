package dev.filinhat.bikecalc.presentation.features.pressure.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import bikecalcmp.composeapp.generated.resources.Res
import bikecalcmp.composeapp.generated.resources.button_close
import bikecalcmp.composeapp.generated.resources.dialog_title_calculation_details
import bikecalcmp.composeapp.generated.resources.dimension_info_inch_format
import bikecalcmp.composeapp.generated.resources.label_bike_weight
import bikecalcmp.composeapp.generated.resources.label_front_wheel
import bikecalcmp.composeapp.generated.resources.label_rear_wheel
import bikecalcmp.composeapp.generated.resources.label_rider_weight
import bikecalcmp.composeapp.generated.resources.label_tire_width
import bikecalcmp.composeapp.generated.resources.label_wheel_size
import bikecalcmp.composeapp.generated.resources.pressure_info_format
import bikecalcmp.composeapp.generated.resources.weight_info_format
import dev.filinhat.bikecalc.domain.model.SavedPressureCalcResult
import dev.filinhat.bikecalc.presentation.theme.BikeCalcTheme
import dev.filinhat.bikecalc.presentation.util.barToKPa
import dev.filinhat.bikecalc.presentation.util.barToPsi
import dev.filinhat.bikecalc.presentation.util.formatDoubleToString
import dev.filinhat.bikecalc.presentation.util.kgToLbs
import dev.filinhat.bikecalc.presentation.util.toBikeDouble
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DetailedInfoDialog(
    result: SavedPressureCalcResult,
    onDismissRequest: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(stringResource(Res.string.dialog_title_calculation_details)) },
        text = {
            Column {
                PressureInfoSection(
                    title = stringResource(Res.string.label_front_wheel),
                    pressureBar = result.pressureFront,
                )

                Spacer(modifier = Modifier.height(8.dp))
                PressureInfoSection(
                    title = stringResource(Res.string.label_rear_wheel),
                    pressureBar = result.pressureRear,
                )

                Spacer(modifier = Modifier.height(8.dp))
                WeightInfoSection(
                    title = stringResource(Res.string.label_rider_weight),
                    weightKg = result.riderWeight,
                )

                Spacer(modifier = Modifier.height(8.dp))
                WeightInfoSection(
                    title = stringResource(Res.string.label_bike_weight),
                    weightKg = result.bikeWeight,
                )

                Spacer(modifier = Modifier.height(8.dp))
                DimensionInfoSection(
                    title = stringResource(Res.string.label_wheel_size),
                    value = result.wheelSize.toBikeDouble(),
                )

                Spacer(modifier = Modifier.height(8.dp))
                DimensionInfoSection(
                    title = stringResource(Res.string.label_tire_width),
                    value = result.tireSize.toBikeDouble(),
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDismissRequest) {
                Text(stringResource(Res.string.button_close))
            }
        },
    )
}

@Composable
private fun PressureInfoSection(
    title: String,
    pressureBar: Double,
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Column(
            modifier = Modifier.padding(start = 8.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    stringResource(
                        Res.string.pressure_info_format,
                        formatDoubleToString(pressureBar),
                        formatDoubleToString(pressureBar.barToPsi()),
                        formatDoubleToString(pressureBar.barToKPa()),
                    ),
                )
            }
        }
    }
}

@Composable
private fun WeightInfoSection(
    title: String,
    weightKg: Double,
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Column(
            modifier = Modifier.padding(start = 8.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    stringResource(
                        Res.string.weight_info_format,
                        formatDoubleToString(weightKg),
                        formatDoubleToString(weightKg.kgToLbs()),
                    ),
                )
            }
        }
    }
}

@Composable
private fun DimensionInfoSection(
    title: String,
    value: Double,
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Column(
            modifier = Modifier.padding(start = 8.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text =
                        stringResource(
                            Res.string.dimension_info_inch_format,
                            formatDoubleToString(value),
                        ),
                )
            }
        }
    }
}

@Preview
@Composable
private fun DetailedInfoDialogPreview() {
    BikeCalcTheme {
        val result =
            SavedPressureCalcResult(
                pressureFront = 2.5,
                pressureRear = 2.8,
                riderWeight = 75.0,
                bikeWeight = 10.0,
                wheelSize = "29",
                tireSize = "2.25",
            )
        DetailedInfoDialog(
            result = result,
            onDismissRequest = {},
        )
    }
}
