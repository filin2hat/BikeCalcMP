package dev.filinhat.bikecalc.core.model.pressure

import kotlinx.serialization.Serializable

/**
 * Результат расчета давления велосипеда
 *
 * @property frontPressure давление переднего колёса
 * @property rearPressure давление заднего колёса
 */
@Serializable
data class PressureCalcResult(
    val frontPressure: Double = 0.0,
    val rearPressure: Double = 0.0,
)