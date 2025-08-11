package dev.filinhat.bikecalc.domain.development.service

import dev.filinhat.bikecalc.core.model.development.DevelopmentCalcParams
import dev.filinhat.bikecalc.core.model.development.DevelopmentCalcResult

/**
 * Сервис для расчёта развития метража (метры на оборот педалей).
 */
interface DevelopmentCalculationService {
    /**
     * Выполняет расчёт развития метража для всех комбинаций передних и задних звёзд.
     *
     * @param params параметры для расчёта развития метража
     * @return список результатов для каждой передачи (комбинации звёзд)
     */
    fun calculateDevelopment(params: DevelopmentCalcParams): List<DevelopmentCalcResult>
}
