package dev.filinhat.bikecalc.domain.pressure.repository

import dev.filinhat.bikecalc.core.model.PressureCalcParams
import dev.filinhat.bikecalc.core.model.PressureCalcResult
import dev.filinhat.bikecalc.core.model.SavedPressureCalcResult
import kotlinx.coroutines.flow.Flow

/**
 * Репозиторий для расчета давления велосипеда.
 */
interface PressureCalcRepository {
    /**
     * Расчет давления колес велосипеда.
     *
     * @param params Параметры расчета.
     */
    fun calcPressure(params: PressureCalcParams): Flow<PressureCalcResult>

    /**
     * Сохранение результата расчета.
     */
    suspend fun saveCalcResult(
        params: PressureCalcParams,
        frontPressure: Double,
        rearPressure: Double,
    )

    /**
     * Удаление результата расчета по его идентификатору.
     */
    suspend fun deleteResult(id: Long)

    /**
     * Удаление всех сохраненных результатов расчета.
     */
    suspend fun deleteAllResults()

    /**
     * Получение всех сохраненных результатов расчета.
     */
    fun getAllResults(): Flow<List<SavedPressureCalcResult>>
}

