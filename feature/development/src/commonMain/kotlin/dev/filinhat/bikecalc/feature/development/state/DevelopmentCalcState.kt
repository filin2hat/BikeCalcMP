package dev.filinhat.bikecalc.feature.development.state

import dev.filinhat.bikecalc.core.model.development.DevelopmentCalcResult
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

/**
 * Состояние экрана расчёта развития метража.
 *
 * @property result Список результатов расчёта (метры на оборот для каждой передачи)
 */
data class DevelopmentCalcState(
    val result: ImmutableList<DevelopmentCalcResult> = persistentListOf(),
)
