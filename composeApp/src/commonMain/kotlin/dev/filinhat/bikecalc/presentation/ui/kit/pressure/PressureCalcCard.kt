package dev.filinhat.bikecalc.presentation.ui.kit.pressure

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import bikecalcmp.composeapp.generated.resources.Res
import bikecalcmp.composeapp.generated.resources.bar
import bikecalcmp.composeapp.generated.resources.front_wheel_pressure
import bikecalcmp.composeapp.generated.resources.kpa
import bikecalcmp.composeapp.generated.resources.psi
import bikecalcmp.composeapp.generated.resources.rear_wheel_pressure
import dev.filinhat.bikecalc.domain.enums.unit.PressureUnit
import dev.filinhat.bikecalc.domain.enums.wheel.Wheel
import dev.filinhat.bikecalc.presentation.ui.theme.BikeCalcTheme
import dev.filinhat.bikecalc.presentation.util.formatValue
import kotlinx.collections.immutable.toImmutableList
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

private const val CARD_HEIGHT = 130

/**
 * Карточка для расчета и просмотра давления велосипеда.
 *
 * @param value давление велосипеда
 * @param wheel тип велосипеда
 * @param modifier модификатор карточки
 */
@Composable
fun PressureCalcCard(
    value: Double,
    wheel: Wheel,
    modifier: Modifier = Modifier,
) {
    var pressureUnit by rememberSaveable { mutableStateOf(PressureUnit.BAR) }

    Card(
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
            ),
        modifier =
            modifier
                .fillMaxWidth()
                .height(CARD_HEIGHT.dp),
        border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.primary),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier =
                Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
        ) {
            Column(
                modifier =
                    Modifier.weight(0.6f),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text =
                        when (wheel) {
                            Wheel.Front -> stringResource(Res.string.front_wheel_pressure)
                            Wheel.Rear -> stringResource(Res.string.rear_wheel_pressure)
                        },
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp),
                    color = MaterialTheme.colorScheme.scrim,
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.Bottom,
                ) {
                    Text(
                        style = MaterialTheme.typography.displayMedium,
                        color = MaterialTheme.colorScheme.scrim,
                        text =
                            when (pressureUnit) {
                                PressureUnit.BAR -> formatValue(value)
                                PressureUnit.KPa -> formatValue(value.barToKPa(), 0)
                                PressureUnit.PSI -> formatValue(value.barToPsi(), 0)
                            },
                    )

                    Text(
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.scrim,
                        text =
                            when (pressureUnit) {
                                PressureUnit.BAR ->
                                    stringResource(Res.string.bar)

                                PressureUnit.KPa ->
                                    stringResource(Res.string.kpa)

                                PressureUnit.PSI ->
                                    stringResource(Res.string.psi)
                            },
                    )
                }
            }

            ChangePressureRadioGroup(
                pressureUnits =
                    listOf(
                        PressureUnit.BAR,
                        PressureUnit.PSI,
                        PressureUnit.KPa,
                    ).toImmutableList(),
                onPressureChange = { unit ->
                    pressureUnit = unit
                },
                modifier =
                    Modifier
                        .weight(0.4f)
                        .padding(start = 16.dp),
            )
        }
    }
}

private fun Double.barToKPa(): Double = this * 100

private fun Double.barToPsi(): Double = this * 14.5038

@Preview
@Composable
private fun PressureCalcCardPreview() {
    BikeCalcTheme {
        PressureCalcCard(
            value = 2.4,
            wheel = Wheel.Front,
        )
    }
}
