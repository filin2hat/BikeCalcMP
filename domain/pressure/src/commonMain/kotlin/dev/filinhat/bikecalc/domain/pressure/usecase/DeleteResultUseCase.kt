package dev.filinhat.bikecalc.domain.pressure.usecase

import dev.filinhat.bikecalc.domain.pressure.repository.PressureCalcRepository

/**
 * Use Case для удаления результата расчета давления
 */
class DeleteResultUseCase(
    private val repository: PressureCalcRepository,
) {
    /**
     * Удаляет результат расчета по идентификатору
     *
     * @param id идентификатор результата для удаления
     */
    suspend operator fun invoke(id: Long) {
        repository.deleteResult(id)
    }
}

