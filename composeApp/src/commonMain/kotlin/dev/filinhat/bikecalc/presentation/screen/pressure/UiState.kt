package dev.filinhat.bikecalc.presentation.screen.pressure

import dev.filinhat.bikecalc.domain.model.PressureCalcResult
import dev.filinhat.bikecalc.presentation.util.UiText

/**
 * Определяет состояния UI, которые может принимать ViewModel.
 */
sealed interface UiState {
    /**
     * Состояние загрузки данных
     */
    data object Loading : UiState

    /**
     * Состояние ошибки
     *
     * @param message Сообщение об ошибке
     */
    data class Error(
        val message: String,
    ) : UiState

    /**
     * Состояние успешного получения данных
     *
     * @param result Результат расчета давления
     * @param selectedTabIndex Индекс выбранного таба
     */
    data class Success(
        val result: PressureCalcResult,
        val selectedTabIndex: Int = 0,
        val isLoading: Boolean = true,
        val errorMessage: UiText? = null,
    ) : UiState
}
