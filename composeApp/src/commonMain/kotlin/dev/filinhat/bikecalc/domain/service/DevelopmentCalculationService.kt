package dev.filinhat.bikecalc.domain.service

import dev.filinhat.bikecalc.domain.model.DevelopmentCalcParams
import dev.filinhat.bikecalc.domain.model.DevelopmentCalcResult

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
