package dev.filinhat.bikecalc.domain.model

data class PressureCoefficients(
    val frontFactor: Double,
    val rearFactor: Double,
    val frontEmpiricalCoefficient: Double,
    val rearEmpiricalCoefficient: Double,
)
