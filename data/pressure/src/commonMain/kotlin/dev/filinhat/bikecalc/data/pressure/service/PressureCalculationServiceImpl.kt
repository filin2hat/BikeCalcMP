package dev.filinhat.bikecalc.data.pressure.service

import dev.filinhat.bikecalc.core.common.util.lbsToKg
import dev.filinhat.bikecalc.core.enums.tube.TubeType
import dev.filinhat.bikecalc.core.enums.unit.WeightUnit
import dev.filinhat.bikecalc.core.enums.wheel.WheelSize
import dev.filinhat.bikecalc.core.model.pressure.PressureCalcParams
import dev.filinhat.bikecalc.core.model.pressure.PressureCalcResult
import dev.filinhat.bikecalc.core.model.pressure.PressureCoefficients
import dev.filinhat.bikecalc.domain.pressure.service.PressureCalculationService

class PressureCalculationServiceImpl : PressureCalculationService {
    override fun calculatePressure(params: PressureCalcParams): PressureCalcResult {
        val coefficients =
            pressureCoefficientsMap[params.wheelSize]
                ?: throw IllegalArgumentException("Unsupported wheel size: ${params.wheelSize}")

        val frontPressure =
            calculateWheelPressure(
                riderWeight = params.riderWeight,
                bikeWeight = params.bikeWeight,
                wheelSize = params.wheelSize.inchesSize,
                tireSize = params.tireSize.tireWidthInMillimeters,
                weightUnit = params.weightUnit,
                coefficients = coefficients,
                isFront = true,
            )
        val rearPressure =
            calculateWheelPressure(
                riderWeight = params.riderWeight,
                bikeWeight = params.bikeWeight,
                wheelSize = params.wheelSize.inchesSize,
                tireSize = params.tireSize.tireWidthInMillimeters,
                weightUnit = params.weightUnit,
                coefficients = coefficients,
                isFront = false,
            )

        return when (params.selectedTubeType) {
            TubeType.TUBES ->
                PressureCalcResult(
                    frontPressure = frontPressure,
                    rearPressure = rearPressure,
                )

            TubeType.TUBELESS ->
                PressureCalcResult(
                    frontPressure = frontPressure * TUBELESS_PRESSURE_COEFFICIENT,
                    rearPressure = rearPressure * TUBELESS_PRESSURE_COEFFICIENT,
                )
        }
    }

    fun calculateWheelPressure(
        riderWeight: Double,
        bikeWeight: Double,
        wheelSize: Double,
        tireSize: Double,
        weightUnit: WeightUnit,
        coefficients: PressureCoefficients,
        isFront: Boolean,
    ): Double {
        require(riderWeight > 0) { "Rider weight must be positive, but was $riderWeight" }
        require(bikeWeight > 0) { "Bike weight must be positive, but was $bikeWeight" }
        require(wheelSize > 0) { "Wheel size must be positive, but was $wheelSize" }
        require(tireSize > 0) { "Tire size must be positive, but was $tireSize" }

        val factor = if (isFront) coefficients.frontFactor else coefficients.rearFactor
        val riderWeightInKg =
            if (weightUnit == WeightUnit.KG) riderWeight else riderWeight.lbsToKg()
        val bikeWeightInKg = if (weightUnit == WeightUnit.KG) bikeWeight else bikeWeight.lbsToKg()

        val empiricalCoefficient =
            if (isFront) coefficients.frontEmpiricalCoefficient else coefficients.rearEmpiricalCoefficient
        return (riderWeightInKg + bikeWeightInKg) * factor / (wheelSize * tireSize) * empiricalCoefficient
    }

    companion object {
        private const val TUBELESS_PRESSURE_COEFFICIENT = 0.85

        private val pressureCoefficientsMap =
            mapOf(
                WheelSize.Inches20 to PressureCoefficients(0.50, 0.60, 85.0, 75.0),
                WheelSize.Inches20BMX to PressureCoefficients(0.50, 0.60, 110.0, 100.0),
                WheelSize.Inches24 to PressureCoefficients(0.50, 0.60, 75.0, 65.0),
                WheelSize.Inches26 to PressureCoefficients(0.49, 0.6, 65.0, 58.0),
                WheelSize.Inches275 to PressureCoefficients(0.5, 0.6, 68.0, 62.5),
                WheelSize.Inches28 to PressureCoefficients(0.42, 0.42, 83.0, 89.0),
                WheelSize.Inches29 to PressureCoefficients(0.52, 0.6, 71.0, 65.0),
            )
    }
}
