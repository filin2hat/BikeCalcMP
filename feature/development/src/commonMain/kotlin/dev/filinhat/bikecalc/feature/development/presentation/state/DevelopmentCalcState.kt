package dev.filinhat.bikecalc.feature.development.presentation.state

import dev.filinhat.bikecalc.core.model.development.DevelopmentCalcResult
import dev.filinhat.bikecalc.feature.development.data.DevelopmentSettings
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

/**
 * Состояние экрана расчёта развития метража.
 * Содержит все данные, необходимые для отображения UI экрана.
 *
 * @property result Список результатов расчёта (метры на оборот для каждой передачи)
 * @property settings Текущие настройки формы (размер колеса, ширина покрышки, зубья звёздочек)
 * @property isLoading Флаг загрузки настроек из хранилища
 */
data class DevelopmentCalcState(
    val result: ImmutableList<DevelopmentCalcResult> = persistentListOf(),
    val settings: DevelopmentSettings = DevelopmentSettings(),
    val isLoading: Boolean = false,
)
