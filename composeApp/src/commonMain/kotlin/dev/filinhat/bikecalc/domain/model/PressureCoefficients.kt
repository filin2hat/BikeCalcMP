package dev.filinhat.bikecalc.domain.model

/**
 * Объект с коэффициентами давления.
 */
data class PressureCoefficients(
    val frontFactor: Double,
    val rearFactor: Double,
    val frontEmpiricalCoefficient: Double,
    val rearEmpiricalCoefficient: Double,
)
