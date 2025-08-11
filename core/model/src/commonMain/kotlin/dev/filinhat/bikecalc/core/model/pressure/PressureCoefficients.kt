package dev.filinhat.bikecalc.core.model.pressure

import kotlinx.serialization.Serializable

/**
 * Объект с коэффициентами давления.
 */
@Serializable
data class PressureCoefficients(
    val frontFactor: Double,
    val rearFactor: Double,
    val frontEmpiricalCoefficient: Double,
    val rearEmpiricalCoefficient: Double,
)