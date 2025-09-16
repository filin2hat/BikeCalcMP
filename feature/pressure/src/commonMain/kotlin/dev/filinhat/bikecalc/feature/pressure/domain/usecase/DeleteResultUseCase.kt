package dev.filinhat.bikecalc.feature.pressure.domain.usecase

import dev.filinhat.bikecalc.feature.pressure.domain.repository.PressureCalcRepository

/**
 * Контракт use case для удаления результата расчета.
 */
interface DeleteResultUseCase {
    suspend operator fun invoke(id: Long)
}

/**
 * Реализация [DeleteResultUseCase].
 */
class DeleteResultUseCaseImpl(
    private val repository: PressureCalcRepository,
) : DeleteResultUseCase {
    override suspend operator fun invoke(id: Long) {
        repository.deleteResult(id)
    }
}
