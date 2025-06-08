package dev.filinhat.bikecalc.presentation.features.development.state

import dev.filinhat.bikecalc.domain.model.DevelopmentCalcParams

/**
 * События экрана расчёта развития метража.
 */
sealed class DevelopmentCalcAction {
    /**
     * Запустить расчёт развития метража.
     * @param params параметры для расчёта
     */
    data class OnCalculateDevelopment(
        val params: DevelopmentCalcParams,
    ) : DevelopmentCalcAction()

    /**
     * Очистить результат.
     */
    object OnClearResult : DevelopmentCalcAction()
} 
