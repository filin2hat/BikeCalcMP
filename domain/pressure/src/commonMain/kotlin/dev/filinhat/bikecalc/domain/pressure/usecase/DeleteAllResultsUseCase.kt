package dev.filinhat.bikecalc.domain.pressure.usecase

import dev.filinhat.bikecalc.domain.pressure.repository.PressureCalcRepository

/**
 * Контракт use case для удаления всех результатов.
 */
interface DeleteAllResultsUseCase {
    suspend operator fun invoke()
}

/**
 * Реализация [DeleteAllResultsUseCase].
 */
class DeleteAllResultsUseCaseImpl(
    private val repository: PressureCalcRepository,
) : DeleteAllResultsUseCase {
    override suspend operator fun invoke() {
        repository.deleteAllResults()
    }
}
