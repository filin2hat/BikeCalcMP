package dev.filinhat.bikecalc.presentation.features.pressure.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import bikecalcmp.composeapp.generated.resources.Res
import bikecalcmp.composeapp.generated.resources.tubeless
import bikecalcmp.composeapp.generated.resources.tubes
import dev.filinhat.bikecalc.domain.enums.tube.TubeType
import dev.filinhat.bikecalc.presentation.theme.BikeCalcTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Селектор выбора типа монташа камеры в покрышке.
 */
@Composable
fun TubeTypeChangeButton(
    onClick: (TubeType) -> Unit,
    enabled: Boolean,
    selectedType: TubeType,
    modifier: Modifier = Modifier,
) {
    var selectType by rememberSaveable { mutableStateOf(selectedType) }
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }
    val items =
        listOf(
            stringResource(Res.string.tubes),
            stringResource(Res.string.tubeless),
        )
    val haptic = LocalHapticFeedback.current

    SingleChoiceSegmentedButtonRow(
        modifier = modifier.fillMaxWidth(),
    ) {
        items.forEachIndexed { index, label ->
            SegmentedButton(
                enabled = enabled,
                shape = MaterialTheme.shapes.medium,
                border =
                    BorderStroke(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.inversePrimary,
                    ),
                colors =
                    SegmentedButtonDefaults.colors(
                        activeContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        activeContentColor = MaterialTheme.colorScheme.scrim,
                        inactiveContainerColor = MaterialTheme.colorScheme.background,
                        inactiveContentColor = MaterialTheme.colorScheme.primary,
                        disabledActiveContainerColor =
                            MaterialTheme.colorScheme.primaryContainer.copy(
                                alpha = 0.3f,
                            ),
                        disabledInactiveContainerColor =
                            MaterialTheme.colorScheme.background.copy(
                                alpha = 0.4f,
                            ),
                        disabledActiveContentColor = MaterialTheme.colorScheme.scrim.copy(alpha = 0.3f),
                        disabledInactiveContentColor =
                            MaterialTheme.colorScheme.inversePrimary.copy(
                                alpha = 0.8f,
                            ),
                    ),
                onClick = {
                    selectedIndex = index
                    selectType =
                        when (index) {
                            0 -> TubeType.TUBES
                            else -> TubeType.TUBELESS
                        }
                    onClick(selectType)
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                },
                selected = index == selectedIndex,
                label = {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.titleMedium,
                    )
                },
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(75.dp)
                        .padding(bottom = 16.dp),
            )
            if (index != items.lastIndex) {
                Spacer(modifier = Modifier.width(12.dp))
            }
        }
    }
}

@Preview
@Composable
private fun TubeTypeChangeButtonPreview() {
    BikeCalcTheme {
        TubeTypeChangeButton(
            onClick = {},
            enabled = true,
            selectedType = TubeType.TUBES,
            modifier = Modifier,
        )
    }
}
