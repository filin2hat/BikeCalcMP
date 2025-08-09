package dev.filinhat.bikecalc.core.enums.unit

import kotlinx.serialization.Serializable

/**
 * Список единиц измерения давления.
 */
@Serializable
enum class PressureUnit {
    BAR,
    PSI,
    KPa,
}
