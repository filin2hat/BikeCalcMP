package dev.filinhat.bikecalc.feature.pressure.data

import dev.filinhat.bikecalc.core.enums.tire.TireSize
import dev.filinhat.bikecalc.core.enums.tube.TubeType
import dev.filinhat.bikecalc.core.enums.unit.WeightUnit
import dev.filinhat.bikecalc.core.enums.wheel.WheelSize
import kotlinx.serialization.Serializable

/**
 * Модель данных для сохранения настроек экрана расчета давления
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
