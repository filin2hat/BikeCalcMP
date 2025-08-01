package dev.filinhat.bikecalc.presentation.features.pressure.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import bikecalcmp.composeapp.generated.resources.clear_results
import dev.filinhat.bikecalc.presentation.theme.BikeCalcTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

private const val BUTTON_HEIGHT = 48

/**
 * Кнопка для удаления результатов.
 */
@Composable
fun DeleteResultsButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val haptic = LocalHapticFeedback.current

    Button(
        onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            onClick()
        },
        shape = MaterialTheme.shapes.medium,
        border =
            BorderStroke(
                width = 2.dp,
                color = MaterialTheme.colorScheme.inversePrimary,
            ),
        modifier =
            modifier
                .fillMaxWidth()
                .height(BUTTON_HEIGHT.dp),
        colors =
            ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.secondary,
            ),
    ) {
        Text(
            text = stringResource(Res.string.clear_results),
            style = MaterialTheme.typography.titleLarge,
            maxLines = 1,
            overflow = Ellipsis,
        )
    }
}

@Preview()
@Composable
private fun PreviewDeleteResultsButtonLight() {
    BikeCalcTheme {
        DeleteResultsButton(
            onClick = {},
        )
    }
}
