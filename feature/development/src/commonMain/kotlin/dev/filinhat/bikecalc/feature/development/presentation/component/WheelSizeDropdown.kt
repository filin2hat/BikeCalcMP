package dev.filinhat.bikecalc.feature.development.presentation.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import dev.filinhat.bikecalc.core.enums.wheel.WheelSize
import dev.filinhat.bikecalc.designsystem.component.DropdownMenu

/**
 * Выпадающий список для выбора размера колеса.
 * Отображает все доступные размеры колес, отсортированные по убыванию диаметра.
 *
 * @param value Текущий выбранный размер колеса
 * @param onValueChange Callback для обработки изменения выбранного размера колеса
 * @param label Текст метки для поля ввода
 * @param modifier Модификатор для настройки внешнего вида компонента
 */
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
