package dev.filinhat.bikecalc.domain.development.usecase

import dev.filinhat.bikecalc.core.model.development.DevelopmentCalcParams
import dev.filinhat.bikecalc.core.model.development.DevelopmentCalcResult
import dev.filinhat.bikecalc.domain.development.repository.DevelopmentCalcRepository
import kotlinx.coroutines.flow.Flow

/**
 * UseCase для расчёта развития метража.
 */
class CalculateDevelopmentUseCase(
    private val repository: DevelopmentCalcRepository,
) {
    operator fun invoke(params: DevelopmentCalcParams): Flow<List<DevelopmentCalcResult>> = repository.calculateDevelopment(params)
}


