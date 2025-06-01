package dev.filinhat.bikecalc.presentation.ui.kit.pressure

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
import bikecalcmp.composeapp.generated.resources.Res
import bikecalcmp.composeapp.generated.resources.bar_btn
import bikecalcmp.composeapp.generated.resources.kpa_btn
import bikecalcmp.composeapp.generated.resources.psi_btn
import dev.filinhat.bikecalc.domain.enums.unit.PressureUnit
import kotlinx.collections.immutable.ImmutableList
import org.jetbrains.compose.resources.stringResource

@Composable
fun ChangePressureRadioGroup(
    pressureUnits: ImmutableList<PressureUnit>,
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
                            PressureUnit.KPa -> stringResource(Res.string.kpa_btn)
                            PressureUnit.BAR -> stringResource(Res.string.bar_btn)
                            PressureUnit.PSI -> stringResource(Res.string.psi_btn)
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
