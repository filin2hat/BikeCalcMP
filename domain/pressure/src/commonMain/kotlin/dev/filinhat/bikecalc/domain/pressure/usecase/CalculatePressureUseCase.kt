package dev.filinhat.bikecalc.domain.pressure.usecase

import dev.filinhat.bikecalc.core.common.DataError
import dev.filinhat.bikecalc.core.common.Result
import dev.filinhat.bikecalc.core.model.PressureCalcParams
import dev.filinhat.bikecalc.core.model.PressureCalcResult
import dev.filinhat.bikecalc.domain.pressure.repository.PressureCalcRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

/**
 * Use Case для расчета давления в колесах велосипеда
 */
class CalculatePressureUseCase(
    private val repository: PressureCalcRepository,
) {
    /**
     * Выполняет расчет давления на основе переданных параметров
     *
     * @param params параметры для расчета
     * @return Flow с результатом расчета, обернутым в Result
     */
    operator fun invoke(params: PressureCalcParams): Flow<Result<PressureCalcResult, DataError.Local>> =
        repository
            .calcPressure(params)
            .map<PressureCalcResult, Result<PressureCalcResult, DataError.Local>> {
                Result.Success(it)
            }.catch { exception ->
                emit(Result.Error(DataError.Local.UNKNOWN_ERROR))
            }
}
