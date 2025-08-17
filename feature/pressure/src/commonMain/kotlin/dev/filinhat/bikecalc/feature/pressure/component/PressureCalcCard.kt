package dev.filinhat.bikecalc.feature.pressure.component

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
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.dp
import bikecalcmp.feature.pressure.generated.resources.Res
import bikecalcmp.feature.pressure.generated.resources.bar
import bikecalcmp.feature.pressure.generated.resources.front_wheel_pressure
import bikecalcmp.feature.pressure.generated.resources.kpa
import bikecalcmp.feature.pressure.generated.resources.psi
import bikecalcmp.feature.pressure.generated.resources.rear_wheel_pressure
import dev.filinhat.bikecalc.core.common.util.barToKPa
import dev.filinhat.bikecalc.core.common.util.barToPsi
import dev.filinhat.bikecalc.core.common.util.formatDoubleToString
import dev.filinhat.bikecalc.core.enums.unit.PressureUnit
import dev.filinhat.bikecalc.core.enums.wheel.Wheel
import org.jetbrains.compose.resources.stringResource

const val CARD_HEIGHT = 130

/**
 * Карточка для расчета и просмотра давления велосипеда.
 *
 * @param value давление велосипеда
 * @param wheel тип колеса
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
                    maxLines = 2,
                    overflow = Ellipsis,
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
                                PressureUnit.BAR -> formatDoubleToString(value)
                                PressureUnit.KPa -> formatDoubleToString(value.barToKPa(), 0)
                                PressureUnit.PSI -> formatDoubleToString(value.barToPsi(), 0)
                            },
                    )

                    Text(
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.scrim,
                        text =
                            when (pressureUnit) {
                                PressureUnit.BAR -> stringResource(Res.string.bar)
                                PressureUnit.KPa -> stringResource(Res.string.kpa)
                                PressureUnit.PSI -> stringResource(Res.string.psi)
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
                    ),
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
