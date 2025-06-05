package dev.filinhat.bikecalc.domain.repository

import dev.filinhat.bikecalc.domain.model.PressureCalcParams
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
    fun calcPressure(params: PressureCalcParams): Flow<PressureCalcResult>

    /**
     * Сохранение результата расчета.
     */
    suspend fun saveCalcResult(
        params: PressureCalcParams,
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
