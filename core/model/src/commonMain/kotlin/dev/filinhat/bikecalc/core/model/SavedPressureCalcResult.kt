package dev.filinhat.bikecalc.core.model

import kotlinx.serialization.Serializable

/**
 * Объект для хранения результатов расчета давления.
 * @property id уникальный идентификатор
 * @property pressureFront давление переднего колеса
 * @property pressureRear давление заднего колеса
 * @property riderWeight вес велосипедиста
 * @property bikeWeight вес велосипеда
 * @property wheelSize размер колеса
 * @property tireSize размер шины
 */
@Serializable
data class SavedPressureCalcResult(
    val id: Long = 0,
    val pressureFront: Double = 0.0,
    val pressureRear: Double = 0.0,
    val riderWeight: Double = 0.0,
    val bikeWeight: Double = 0.0,
    val wheelSize: String = "",
    val tireSize: String = "",
)

