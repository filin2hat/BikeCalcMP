package dev.filinhat.bikecalc.data.development.repository

import dev.filinhat.bikecalc.core.model.development.DevelopmentCalcParams
import dev.filinhat.bikecalc.core.model.development.DevelopmentCalcResult
import dev.filinhat.bikecalc.domain.development.repository.DevelopmentCalcRepository
import dev.filinhat.bikecalc.domain.development.service.DevelopmentCalculationService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Реализация репозитория для расчёта развития метража (метры на оборот педалей).
 *
 * @property service сервис для выполнения расчёта развития метража
 */
class DevelopmentCalcRepositoryImpl(
    private val service: DevelopmentCalculationService,
) : DevelopmentCalcRepository {
    /**
     * Выполняет расчёт развития метража и возвращает результаты в виде Flow.
     *
     * @param params параметры для расчёта развития метража
     * @return Flow со списком результатов для каждой передачи
     */
    override fun calculateDevelopment(params: DevelopmentCalcParams): Flow<List<DevelopmentCalcResult>> =
        flow {
            emit(service.calculateDevelopment(params))
        }
}
