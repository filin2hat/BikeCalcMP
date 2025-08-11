package dev.filinhat.bikecalc.domain.pressure.usecase

import dev.filinhat.bikecalc.core.model.pressure.SavedPressureCalcResult
import dev.filinhat.bikecalc.domain.pressure.repository.PressureCalcRepository
import kotlinx.coroutines.flow.Flow

/**
 * Контракт use case для получения сохраненных результатов расчета давления.
 */
interface GetSavedResultsUseCase {
    operator fun invoke(): Flow<List<SavedPressureCalcResult>>
}

/**
 * Реализация [GetSavedResultsUseCase].
 */
class GetSavedResultsUseCaseImpl(
    private val repository: PressureCalcRepository,
) : GetSavedResultsUseCase {
    override operator fun invoke(): Flow<List<SavedPressureCalcResult>> = repository.getAllResults()
}
