package dev.filinhat.bikecalc.data.service

import dev.filinhat.bikecalc.domain.enums.tube.TubeType
import dev.filinhat.bikecalc.domain.enums.unit.WeightUnit
import dev.filinhat.bikecalc.domain.enums.wheel.WheelSize
import dev.filinhat.bikecalc.domain.model.PressureCalcParams
import dev.filinhat.bikecalc.domain.model.PressureCalcResult
import dev.filinhat.bikecalc.domain.model.PressureCoefficients
import dev.filinhat.bikecalc.domain.service.PressureCalculationService

class PressureCalculationServiceImpl : PressureCalculationService {
    override fun calculatePressure(params: PressureCalcParams): PressureCalcResult {
        val coefficients =
            pressureCoefficientsMap[params.wheelSize]
                ?: throw IllegalArgumentException("Unsupported wheel size: ${params.wheelSize}")

        val frontPressure =
            calculateWheelPressure(
                params.riderWeight,
                params.bikeWeight,
                params.wheelSize.inchesSize,
                params.tireSize.tireWidthInMillimeters,
                params.weightUnit,
                coefficients,
                isFront = true,
            )
        val rearPressure =
            calculateWheelPressure(
                params.riderWeight,
                params.bikeWeight,
                params.wheelSize.inchesSize,
                params.tireSize.tireWidthInMillimeters,
                params.weightUnit,
                coefficients,
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

    private fun calculateWheelPressure(
        riderWeight: Double,
        bikeWeight: Double,
        wheelSize: Double,
        tireSize: Double,
        weightUnit: WeightUnit,
        coefficients: PressureCoefficients,
        isFront: Boolean,
    ): Double {
        val factor = if (isFront) coefficients.frontFactor else coefficients.rearFactor

        val riderWeight =
            if (weightUnit == WeightUnit.KG) riderWeight else lbsToKg(riderWeight)
        val bikeWeight =
            if (weightUnit == WeightUnit.KG) bikeWeight else lbsToKg(bikeWeight)
        val empiricalCoefficient =
            if (isFront) coefficients.frontEmpiricalCoefficient else coefficients.rearEmpiricalCoefficient

        return ((riderWeight * factor + bikeWeight * factor) / (wheelSize * tireSize)) * empiricalCoefficient
    }

    private fun lbsToKg(lbs: Double) = lbs / 2.20462

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
