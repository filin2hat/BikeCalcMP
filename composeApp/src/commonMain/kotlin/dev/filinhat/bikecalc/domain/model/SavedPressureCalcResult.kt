package dev.filinhat.bikecalc.domain.model

data class SavedPressureCalcResult(
    val pressureFront: Double = 0.0,
    val pressureRear: Double = 0.0,
    val riderWeight: Double = 0.0,
    val bikeWeight: Double = 0.0,
    val wheelSize: String = "",
    val tireSize: String = "",
)
