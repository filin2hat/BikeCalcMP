package dev.filinhat.bikecalc.domain.repository

import dev.filinhat.bikecalc.domain.enums.tire.TireSize
import dev.filinhat.bikecalc.domain.enums.tube.TubeType
import dev.filinhat.bikecalc.domain.enums.unit.WeightUnit
import dev.filinhat.bikecalc.domain.enums.wheel.WheelSize
import dev.filinhat.bikecalc.domain.model.PressureCalcResult
import dev.filinhat.bikecalc.domain.model.SavedPressureCalcResult
import kotlinx.coroutines.flow.Flow

/**
 * Репозиторий для расчета давления велосипеда.
 */
interface PressureCalcRepository {
    /**
     * Расчет давления колес велосипеда.
     */
    fun calcPressure(
        riderWeight: Double,
        bikeWeight: Double,
        wheelSize: WheelSize,
        tireSize: TireSize,
        weightUnit: WeightUnit,
        selectedTubeType: TubeType,
    ): Flow<PressureCalcResult>

    /**
     * Сохранение результата расчета.
     */
    suspend fun saveCalcResult(
        riderWeight: Double,
        bikeWeight: Double,
        wheelSize: WheelSize,
        tireSize: TireSize,
        selectedTubeType: TubeType,
        frontPressureTubes: Double,
        rearPressureTubes: Double,
        frontPressureTubeless: Double,
        rearPressureTubeless: Double,
    )

    /**
     * Удаление всех сохраненных результатов расчета.
     */
    suspend fun deleteAllResults()

    /**
     * Получение всех сохраненных результатов расчета.
     */
    fun getAllResults(): Flow<List<SavedPressureCalcResult>>
}
