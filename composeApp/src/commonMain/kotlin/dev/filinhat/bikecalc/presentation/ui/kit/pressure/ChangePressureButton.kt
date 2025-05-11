package dev.filinhat.bikecalc.presentation.ui.kit.pressure

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import bikecalcmp.composeapp.generated.resources.Res
import bikecalcmp.composeapp.generated.resources.bar_btn
import bikecalcmp.composeapp.generated.resources.kpa_btn
import bikecalcmp.composeapp.generated.resources.psi_btn
import dev.filinhat.bikecalc.domain.enums.unit.PressureUnit
import org.jetbrains.compose.resources.stringResource

private const val BUTTON_WIDTH = 75

@Composable
fun ChangePressureButton(
    onPressureChange: (pressureUnit: PressureUnit) -> Unit,
    pressureUnit: PressureUnit,
    modifier: Modifier = Modifier,
) {
    ElevatedButton(
        onClick = {
            onPressureChange(pressureUnit)
        },
        border =
            BorderStroke(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary,
            ),
        shape = MaterialTheme.shapes.medium,
        modifier = modifier.width(BUTTON_WIDTH.dp),
        colors =
            ButtonDefaults.elevatedButtonColors(),
    ) {
        Text(
            text =
                when (pressureUnit) {
                    PressureUnit.KPa -> stringResource(Res.string.kpa_btn)
                    PressureUnit.BAR -> stringResource(Res.string.bar_btn)
                    PressureUnit.PSI -> stringResource(Res.string.psi_btn)
                },
            style = MaterialTheme.typography.labelLarge,
        )
    }
}
