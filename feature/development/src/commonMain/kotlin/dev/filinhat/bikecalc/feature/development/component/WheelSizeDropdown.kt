package dev.filinhat.bikecalc.feature.development.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import dev.filinhat.bikecalc.core.enums.wheel.WheelSize
import dev.filinhat.bikecalc.designsystem.component.DropdownMenu

@Composable
fun WheelSizeDropdown(
    value: WheelSize?,
    onValueChange: (WheelSize) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
) {
    val items = remember { WheelSize.entries.sortedByDescending { it.etrtoMm }.toList() }
    DropdownMenu(
        onItemSelect = onValueChange,
        items = items,
        value = value,
        label = label,
        itemLabel = { it?.nameSize },
        modifier = modifier,
    )
}
