package dev.filinhat.bikecalc.domain.model

import dev.filinhat.bikecalc.domain.enums.tire.TireSize
import dev.filinhat.bikecalc.domain.enums.tube.TubeType
import dev.filinhat.bikecalc.domain.enums.unit.WeightUnit
import dev.filinhat.bikecalc.domain.enums.wheel.WheelSize

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
data class PressureCalcParams(
    val riderWeight: Double,
    val bikeWeight: Double,
    val wheelSize: WheelSize,
    val tireSize: TireSize,
    val weightUnit: WeightUnit,
    val selectedTubeType: TubeType,
)
