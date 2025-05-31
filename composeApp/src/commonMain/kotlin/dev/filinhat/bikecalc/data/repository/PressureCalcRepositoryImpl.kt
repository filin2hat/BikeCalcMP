package dev.filinhat.bikecalc.data.repository

import dev.filinhat.bikecalc.data.database.PressureResultsDao
import dev.filinhat.bikecalc.domain.entity.PressureResultEntity
import dev.filinhat.bikecalc.domain.enums.tire.TireSize
import dev.filinhat.bikecalc.domain.enums.tube.TubeType
import dev.filinhat.bikecalc.domain.enums.unit.WeightUnit
import dev.filinhat.bikecalc.domain.enums.wheel.WheelSize
import dev.filinhat.bikecalc.domain.mapper.toSavedPressureCalcResult
import dev.filinhat.bikecalc.domain.model.PressureCalcResult
import dev.filinhat.bikecalc.domain.model.PressureCoefficients
import dev.filinhat.bikecalc.domain.model.SavedPressureCalcResult
import dev.filinhat.bikecalc.domain.repository.PressureCalcRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

private const val TUBELESS_PRESSURE_COEFFICIENT = 0.85

class PressureCalcRepositoryImpl(
    private val pressureResultDao: PressureResultsDao,
) : PressureCalcRepository {
    override fun calcPressure(
        riderWeight: Double,
        bikeWeight: Double,
        wheelSize: WheelSize,
        tireSize: TireSize,
        weightUnit: WeightUnit,
        selectedTubeType: TubeType,
    ): Flow<PressureCalcResult> =
        flow {
            val coefficients =
                pressureCoefficientsMap[wheelSize]
                    ?: throw IllegalArgumentException("Unsupported wheel size: $wheelSize")

            val frontPressureTubes =
                calculatePressure(
                    riderWeight,
                    bikeWeight,
                    wheelSize.inchesSize,
                    tireSize.tireWidthInMillimeters,
                    weightUnit,
                    coefficients,
                    isFront = true,
                )
            val rearPressureTubes =
                calculatePressure(
                    riderWeight,
                    bikeWeight,
                    wheelSize.inchesSize,
                    tireSize.tireWidthInMillimeters,
                    weightUnit,
                    coefficients,
                    isFront = false,
                )
            val frontPressureTubeless = frontPressureTubes * TUBELESS_PRESSURE_COEFFICIENT
            val rearPressureTubeless = rearPressureTubes * TUBELESS_PRESSURE_COEFFICIENT
            saveCalcResult(
                riderWeight = riderWeight,
                bikeWeight = bikeWeight,
                wheelSize = wheelSize,
                tireSize = tireSize,
                selectedTubeType = selectedTubeType,
                frontPressureTubes = frontPressureTubes,
                rearPressureTubes = rearPressureTubes,
                frontPressureTubeless = frontPressureTubeless,
                rearPressureTubeless = rearPressureTubeless,
            )
            emit(
                PressureCalcResult(
                    tubesFront = frontPressureTubes,
                    tubesRear = rearPressureTubes,
                    tubelessFront = frontPressureTubeless,
                    tubelessRear = rearPressureTubeless,
                ),
            )
        }

    override suspend fun saveCalcResult(
        riderWeight: Double,
        bikeWeight: Double,
        wheelSize: WheelSize,
        tireSize: TireSize,
        selectedTubeType: TubeType,
        frontPressureTubes: Double,
        rearPressureTubes: Double,
        frontPressureTubeless: Double,
        rearPressureTubeless: Double,
    ) {
        val entity =
            PressureResultEntity(
                riderWeight = riderWeight,
                bikeWeight = bikeWeight,
                wheelSize = wheelSize.inchesSize.toString(),
                tireSize = tireSize.tireWidthInInches.toString(),
                pressureFront = if (selectedTubeType == TubeType.TUBES) frontPressureTubes else frontPressureTubeless,
                pressureRear = if (selectedTubeType == TubeType.TUBES) rearPressureTubes else rearPressureTubeless,
                id = null,
            )
        pressureResultDao.upsert(entity)
    }

    override suspend fun deleteAllResults() {
        pressureResultDao.deleteAllResults()
    }

    override fun getAllResults(): Flow<List<SavedPressureCalcResult>> =
        pressureResultDao
            .getAllResults()
            .map { entities ->
                entities.map { entity ->
                    entity.toSavedPressureCalcResult()
                }
            }

    private fun calculatePressure(
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
