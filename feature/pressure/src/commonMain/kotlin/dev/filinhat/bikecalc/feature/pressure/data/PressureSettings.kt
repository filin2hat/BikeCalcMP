package dev.filinhat.bikecalc.feature.pressure.data

import dev.filinhat.bikecalc.core.enums.tire.TireSize
import dev.filinhat.bikecalc.core.enums.tube.TubeType
import dev.filinhat.bikecalc.core.enums.unit.WeightUnit
import dev.filinhat.bikecalc.core.enums.wheel.WheelSize
import kotlinx.serialization.Serializable

/**
 * Модель данных для сохранения настроек экрана расчета давления.
 * Содержит все параметры, необходимые для расчета давления велосипеда.
 *
 * @property riderWeight Вес велосипедиста в виде строки (по умолчанию пустая строка)
 * @property bikeWeight Вес велосипеда в виде строки (по умолчанию пустая строка)
 * @property wheelSize Размер колеса велосипеда (по умолчанию null)
 * @property tireSize Размер покрышки (по умолчанию null)
 * @property weightUnit Единица измерения веса (по умолчанию KG)
 * @property selectedTubeType Выбранный тип монтажа покрышки (по умолчанию TUBES)
 */
@Serializable
data class PressureSettings(
    val riderWeight: String = "",
    val bikeWeight: String = "",
    val wheelSize: WheelSize? = null,
    val tireSize: TireSize? = null,
    val weightUnit: WeightUnit = WeightUnit.KG,
    val selectedTubeType: TubeType = TubeType.TUBES,
)
