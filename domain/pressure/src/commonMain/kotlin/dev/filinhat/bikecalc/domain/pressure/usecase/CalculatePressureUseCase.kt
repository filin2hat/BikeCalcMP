package dev.filinhat.bikecalc.domain.pressure.usecase

import dev.filinhat.bikecalc.core.common.DataError
import dev.filinhat.bikecalc.core.common.Result
import dev.filinhat.bikecalc.core.model.pressure.PressureCalcParams
import dev.filinhat.bikecalc.core.model.pressure.PressureCalcResult
import dev.filinhat.bikecalc.domain.pressure.repository.PressureCalcRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

/**
 * Контракт use case для расчета давления в колесах велосипеда.
 */
interface CalculatePressureUseCase {
    operator fun invoke(params: PressureCalcParams): Flow<Result<PressureCalcResult, DataError.Local>>
}

/**
 * Реализация [CalculatePressureUseCase].
 */
class CalculatePressureUseCaseImpl(
    private val repository: PressureCalcRepository,
) : CalculatePressureUseCase {
    override fun invoke(params: PressureCalcParams): Flow<Result<PressureCalcResult, DataError.Local>> =
        repository
            .calcPressure(params)
            .map<PressureCalcResult, Result<PressureCalcResult, DataError.Local>> {
                Result.Success(it)
            }.catch {
                emit(Result.Error(DataError.Local.UNKNOWN_ERROR))
            }
}
