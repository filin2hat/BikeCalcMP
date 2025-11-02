package dev.filinhat.bikecalc.feature.pressure.domain.usecase

import dev.filinhat.bikecalc.feature.pressure.domain.repository.PressureCalcRepository

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
