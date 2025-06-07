package dev.filinhat.bikecalc.domain.model

/**
 * Результат расчета давления велосипеда
 *
 * @property frontPressure давление переднего колёса
 * @property rearPressure давление заднего колёса
 */
data class PressureCalcResult(
    val frontPressure: Double = 0.0,
    val rearPressure: Double = 0.0,
)
