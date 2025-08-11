package dev.filinhat.bikecalc.feature.development.state

import dev.filinhat.bikecalc.core.model.development.DevelopmentCalcParams

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
