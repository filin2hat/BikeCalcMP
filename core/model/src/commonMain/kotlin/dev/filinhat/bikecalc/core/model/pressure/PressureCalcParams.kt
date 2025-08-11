package dev.filinhat.bikecalc.core.model.pressure

import dev.filinhat.bikecalc.core.enums.tire.TireSize
import dev.filinhat.bikecalc.core.enums.tube.TubeType
import dev.filinhat.bikecalc.core.enums.unit.WeightUnit
import dev.filinhat.bikecalc.core.enums.wheel.WheelSize
import kotlinx.serialization.Serializable

/**
 * Параметры для расчета давления в колесах велосипеда.
 *
 * @property riderWeight вес велосипедиста
 * @property bikeWeight вес велосипеда
 * @property wheelSize размер колеса
 * @property tireSize размер шины
 * @property weightUnit единица измерения веса
 * @property selectedTubeType тип монтажа камеры
 */
@Serializable
data class PressureCalcParams(
    val riderWeight: Double,
    val bikeWeight: Double,
    val wheelSize: WheelSize,
    val tireSize: TireSize,
    val weightUnit: WeightUnit,
    val selectedTubeType: TubeType,
)