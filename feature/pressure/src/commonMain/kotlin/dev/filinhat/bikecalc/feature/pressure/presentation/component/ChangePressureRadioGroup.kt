package dev.filinhat.bikecalc.feature.pressure.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import bikecalcmp.feature.pressure.generated.resources.Res
import bikecalcmp.feature.pressure.generated.resources.bar
import bikecalcmp.feature.pressure.generated.resources.kpa
import bikecalcmp.feature.pressure.generated.resources.psi
import dev.filinhat.bikecalc.core.enums.unit.PressureUnit
import org.jetbrains.compose.resources.stringResource

/**
 * Радио кнопки для выбора единиц отображения давления.
 * Позволяет пользователю выбрать предпочитаемые единицы измерения давления:
 * BAR, PSI или KPa. Использует радио кнопки для выбора одной опции из списка.
 *
 * @param pressureUnits Список доступных единиц измерения давления
 * @param onPressureChange Callback для обработки изменения выбранной единицы измерения
 * @param modifier Модификатор для настройки внешнего вида компонента
 */
@Composable
fun ChangePressureRadioGroup(
    pressureUnits: List<PressureUnit>,
    onPressureChange: (pressureUnit: PressureUnit) -> Unit,
    modifier: Modifier = Modifier,
) {
    val (selectedOption, onOptionSelected) = rememberSaveable { mutableStateOf(pressureUnits[0]) }
    val haptic = LocalHapticFeedback.current

    Column(
        modifier
            .padding(vertical = 8.dp)
            .fillMaxHeight()
            .selectableGroup(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        pressureUnits.forEach { item ->
            Row(
                Modifier
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.inversePrimary,
                        MaterialTheme.shapes.medium,
                    ).clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.background)
                    .height(32.dp)
                    .fillMaxWidth()
                    .selectable(
                        selected = (item == selectedOption),
                        onClick = {
                            onOptionSelected(item)
                            onPressureChange(item)
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        },
                        role = Role.RadioButton,
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(
                    selected = (item == selectedOption),
                    onClick = null,
                    modifier = Modifier.padding(start = 4.dp),
                    colors =
                        RadioButtonDefaults.colors(
                            selectedColor = MaterialTheme.colorScheme.primary,
                            unselectedColor = MaterialTheme.colorScheme.onBackground,
                        ),
                )
                Text(
                    text =
                        when (item) {
                            PressureUnit.KPa -> stringResource(Res.string.kpa)
                            PressureUnit.BAR -> stringResource(Res.string.bar)
                            PressureUnit.PSI -> stringResource(Res.string.psi)
                        },
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (item == selectedOption) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(start = 6.dp),
                )
            }
            if (pressureUnits.indexOf(item) < pressureUnits.lastIndex) {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
