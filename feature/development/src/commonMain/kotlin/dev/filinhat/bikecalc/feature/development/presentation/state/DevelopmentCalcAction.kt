package dev.filinhat.bikecalc.feature.development.presentation.state

import dev.filinhat.bikecalc.core.model.development.DevelopmentCalcParams
import dev.filinhat.bikecalc.feature.development.data.DevelopmentSettings

/**
 * События экрана расчёта развития метража.
 * Определяет все возможные действия пользователя на экране.
 */
sealed class DevelopmentCalcAction {
    /**
     * Запустить расчёт развития метража.
     * Выполняется при нажатии кнопки расчёта с введёнными параметрами.
     *
     * @param params Параметры для расчёта развития метража
     */
    data class OnCalculateDevelopment(
        val params: DevelopmentCalcParams,
    ) : DevelopmentCalcAction()

    /**
     * Очистить результат расчёта.
     * Удаляет все отображённые результаты и графики.
     */
    object OnClearResult : DevelopmentCalcAction()

    /**
     * Обновить настройки в UI.
     * Изменяет отображаемые настройки без сохранения в постоянное хранилище.
     *
     * @param settings Новые настройки для отображения
     */
    data class OnUpdateSettings(
        val settings: DevelopmentSettings,
    ) : DevelopmentCalcAction()

    /**
     * Сохранить настройки в постоянное хранилище.
     * Сохраняет настройки для последующего использования.
     *
     * @param settings Настройки для сохранения
     */
    data class OnSaveSettings(
        val settings: DevelopmentSettings,
    ) : DevelopmentCalcAction()
}
