package dev.filinhat.bikecalc.feature.development.data

import dev.filinhat.bikecalc.core.enums.wheel.WheelSize
import kotlinx.serialization.Serializable

/**
 * Модель данных для сохранения настроек экрана развития метража
 */
@Serializable
data class DevelopmentSettings(
    val wheelSize: WheelSize = WheelSize.Inches29,
    val tireWidth: String = "57",
    val frontTeethInputs: List<String> = listOf("32"),
    val rearTeeth: String = "10,12,14,16,18,21,24,28,33,39,45,51",
)
