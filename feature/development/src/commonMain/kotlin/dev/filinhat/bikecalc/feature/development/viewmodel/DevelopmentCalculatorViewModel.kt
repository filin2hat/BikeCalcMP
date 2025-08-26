package dev.filinhat.bikecalc.feature.development.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.filinhat.bikecalc.core.model.development.DevelopmentCalcParams
import dev.filinhat.bikecalc.core.presentation.BaseViewModel
import dev.filinhat.bikecalc.core.settings.SettingsStore
import dev.filinhat.bikecalc.domain.development.usecase.CalculateDevelopmentUseCase
import dev.filinhat.bikecalc.feature.development.data.DevelopmentSettings
import dev.filinhat.bikecalc.feature.development.state.DevelopmentCalcAction
import dev.filinhat.bikecalc.feature.development.state.DevelopmentCalcState
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel для экрана расчёта развития метража.
 * Управляет состоянием UI, обработкой действий пользователя и сохранением настроек.
 *
 * @property calculateDevelopmentUseCase UseCase для расчёта развития метража
 * @property settingsStore Хранилище настроек экрана
 */
class DevelopmentCalculatorViewModel(
    private val calculateDevelopmentUseCase: CalculateDevelopmentUseCase,
    private val settingsStore: SettingsStore<DevelopmentSettings>,
) : ViewModel(),
    BaseViewModel<DevelopmentCalcState, DevelopmentCalcAction> {
    private val _uiState = MutableStateFlow(DevelopmentCalcState())
    override val uiState: StateFlow<DevelopmentCalcState> =
        _uiState.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = _uiState.value,
        )

    init {
        loadSettings()
    }

    /**
     * Обрабатывает действия пользователя.
     *
     * @param event Действие для обработки
     */
    override fun onAction(event: DevelopmentCalcAction) =
        when (event) {
            is DevelopmentCalcAction.OnCalculateDevelopment -> calculateDevelopment(event.params)
            is DevelopmentCalcAction.OnClearResult -> clearResult()
            is DevelopmentCalcAction.OnUpdateSettings -> updateSettings(event.settings)
            is DevelopmentCalcAction.OnSaveSettings -> saveSettings(event.settings)
        }

    /**
     * Загружает сохранённые настройки из хранилища.
     * При ошибке загрузки используются дефолтные значения.
     */
    private fun loadSettings() {
        _uiState.update { it.copy(isLoading = true) }

        settingsStore
            .getSettings()
            .onEach { settings ->
                _uiState.update {
                    it.copy(
                        settings = settings,
                        isLoading = false,
                    )
                }
            }.catch { error ->
                // В случае ошибки используем дефолтные значения
                _uiState.update {
                    it.copy(isLoading = false)
                }
            }.launchIn(viewModelScope)
    }

    /**
     * Обновляет настройки в UI состоянии.
     *
     * @param settings Новые настройки для обновления
     */
    private fun updateSettings(settings: dev.filinhat.bikecalc.feature.development.data.DevelopmentSettings) {
        _uiState.update { it.copy(settings = settings) }
    }

    /**
     * Сохраняет настройки в постоянное хранилище.
     *
     * @param settings Настройки для сохранения
     */
    private fun saveSettings(settings: dev.filinhat.bikecalc.feature.development.data.DevelopmentSettings) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                settingsStore.saveSettings(settings)
            } catch (error: Exception) {
                // Обработка ошибки сохранения
            }
        }
    }

    /**
     * Выполняет расчёт развития метража на основе переданных параметров.
     *
     * @param params Параметры для расчёта развития метража
     */
    private fun calculateDevelopment(params: DevelopmentCalcParams) {
        viewModelScope.launch(Dispatchers.IO) {
            calculateDevelopmentUseCase(params).collect { results ->
                _uiState.update { it.copy(result = results.toImmutableList()) }
            }
        }
    }

    /**
     * Очищает результаты расчёта.
     */
    private fun clearResult() {
        _uiState.update { it.copy(result = persistentListOf()) }
    }
}
