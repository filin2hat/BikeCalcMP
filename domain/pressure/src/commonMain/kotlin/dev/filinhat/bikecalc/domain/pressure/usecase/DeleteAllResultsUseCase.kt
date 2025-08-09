package dev.filinhat.bikecalc.domain.pressure.usecase

import dev.filinhat.bikecalc.domain.pressure.repository.PressureCalcRepository

/**
 * Use Case для удаления всех результатов расчета давления
 */
class DeleteAllResultsUseCase(
    private val repository: PressureCalcRepository,
) {
    /**
     * Удаляет все сохраненные результаты расчета
     */
    suspend operator fun invoke() {
        repository.deleteAllResults()
    }
}

