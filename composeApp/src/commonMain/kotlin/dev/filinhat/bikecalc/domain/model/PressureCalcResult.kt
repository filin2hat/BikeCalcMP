package dev.filinhat.bikecalc.domain.model

/**
 * Результат расчета давления велосипеда
 *
 * @property tubesFront давление переднего колёса с камерой
 * @property tubesRear давление заднего колёса с камерой
 * @property tubelessFront давление переднего колёса без камеры
 * @property tubelessRear давление заднего колёса без камеры
 */
data class PressureCalcResult(
    val tubesFront: Double = 0.0,
    val tubesRear: Double = 0.0,
    val tubelessFront: Double = 0.0,
    val tubelessRear: Double = 0.0,
)
