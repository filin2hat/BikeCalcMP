package dev.filinhat.bikecalc.domain.development.repository

import dev.filinhat.bikecalc.core.model.development.DevelopmentCalcParams
import dev.filinhat.bikecalc.core.model.development.DevelopmentCalcResult
import kotlinx.coroutines.flow.Flow

/**
 * Репозиторий для расчёта развития метража (метры на оборот педалей).
 */
interface DevelopmentCalcRepository {
    /**
     * Выполняет расчёт развития метража и возвращает результаты в виде Flow.
     *
     * @param params параметры для расчёта развития метража
     * @return Flow со списком результатов для каждой передачи
     */
    fun calculateDevelopment(params: DevelopmentCalcParams): Flow<List<DevelopmentCalcResult>>
}
