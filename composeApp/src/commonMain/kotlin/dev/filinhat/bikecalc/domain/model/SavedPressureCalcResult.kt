package dev.filinhat.bikecalc.domain.model

/**
 * Результат расчета давления велосипеда
 *
 * @property tubesFront давление переднего колёса с камерой
 * @property tubesRear давление заднего колёса с камерой
 * @property tubelessFront давление переднего колёса без камеры
 * @property tubelessRear давление заднего колёса без камеры
 */
data class SavedPressureCalcResult(
    val pressureFront: Double = 0.0,
    val pressureRear: Double = 0.0,
    val riderWeight: Double = 0.0,
    val bikeWeight: Double = 0.0,
    val wheelSize: String = "",
    val tireSize: String = "",
)
