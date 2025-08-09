package dev.filinhat.bikecalc.domain.pressure.usecase

import dev.filinhat.bikecalc.core.model.SavedPressureCalcResult
import dev.filinhat.bikecalc.domain.pressure.repository.PressureCalcRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use Case для получения сохраненных результатов расчета давления
 */
class GetSavedResultsUseCase(
    private val repository: PressureCalcRepository,
) {
    /**
     * Получает все сохраненные результаты расчета
     *
     * @return Flow со списком сохраненных результатов
     */
    operator fun invoke(): Flow<List<SavedPressureCalcResult>> = repository.getAllResults()
}

