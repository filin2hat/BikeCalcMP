package dev.filinhat.bikecalc.presentation.features.pressure.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.dp
import bikecalcmp.composeapp.generated.resources.Res
import bikecalcmp.composeapp.generated.resources.calculate_pressure
import dev.filinhat.bikecalc.presentation.theme.BikeCalcTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Кнопка для расчета давления.
 */
@Composable
fun CalculatePressureButton(
    onClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier,
) {
    val haptic = LocalHapticFeedback.current

    Button(
        onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.Confirm)
            onClick()
        },
        enabled = enabled,
        shape = MaterialTheme.shapes.medium,
        border =
            BorderStroke(
                width = 2.dp,
                color = MaterialTheme.colorScheme.inversePrimary,
            ),
        modifier =
            modifier
                .fillMaxWidth()
                .height(75.dp)
                .padding(bottom = 18.dp),
        colors =
            ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.scrim,
            ),
    ) {
        Text(
            text = stringResource(Res.string.calculate_pressure),
            style = MaterialTheme.typography.titleLarge,
            maxLines = 1,
            overflow = Ellipsis,
        )
    }
}

@Preview
@Composable
private fun CalculatePressureButtonPreview() {
    BikeCalcTheme {
        CalculatePressureButton(onClick = {}, enabled = true)
    }
}
