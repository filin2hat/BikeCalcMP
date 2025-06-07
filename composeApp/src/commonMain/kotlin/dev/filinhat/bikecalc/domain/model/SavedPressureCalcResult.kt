package dev.filinhat.bikecalc.domain.model

/**
 * Объект для хранения результатов расчета давления.
 * @property pressureFront давление переднего колеса
 * @property pressureRear давление заднего колеса
 * @property riderWeight вес велосипедиста
 * @property bikeWeight вес велосипеда
 * @property wheelSize размер колеса
 * @property tireSize размер шины
 */
data class SavedPressureCalcResult(
    val pressureFront: Double = 0.0,
    val pressureRear: Double = 0.0,
    val riderWeight: Double = 0.0,
    val bikeWeight: Double = 0.0,
    val wheelSize: String = "",
    val tireSize: String = "",
)
