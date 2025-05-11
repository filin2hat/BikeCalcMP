package dev.filinhat.bikecalc.presentation.util

import kotlinx.coroutines.flow.StateFlow

/**
 * Базовый интерфейс для всех ViewModel.
 *
 * @param UiState состояние UI
 * @param UiEvent событие UI
 */
interface BaseViewModel<UiState, UiEvent> {
    /**
     * Состояние UI.
     */
    val uiState: StateFlow<UiState>

    /**
     * Обработать событие UI.
     */
    fun perform(event: UiEvent)
}
