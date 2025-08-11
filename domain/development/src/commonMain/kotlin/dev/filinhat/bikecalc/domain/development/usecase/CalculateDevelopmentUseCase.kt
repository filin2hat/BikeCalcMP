package dev.filinhat.bikecalc.domain.development.usecase

import dev.filinhat.bikecalc.core.model.development.DevelopmentCalcParams
import dev.filinhat.bikecalc.core.model.development.DevelopmentCalcResult
import dev.filinhat.bikecalc.domain.development.repository.DevelopmentCalcRepository
import kotlinx.coroutines.flow.Flow

/**
 * Контракт use case для расчёта развития метража.
 */
interface CalculateDevelopmentUseCase {
    operator fun invoke(params: DevelopmentCalcParams): Flow<List<DevelopmentCalcResult>>
}

/**
 * Реализация [CalculateDevelopmentUseCase].
 */
class CalculateDevelopmentUseCaseImpl(
    private val repository: DevelopmentCalcRepository,
) : CalculateDevelopmentUseCase {
    override fun invoke(params: DevelopmentCalcParams): Flow<List<DevelopmentCalcResult>> = repository.calculateDevelopment(params)
}
