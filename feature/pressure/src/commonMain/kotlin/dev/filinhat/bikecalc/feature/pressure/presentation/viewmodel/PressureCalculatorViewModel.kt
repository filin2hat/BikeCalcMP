package dev.filinhat.bikecalc.feature.pressure.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.filinhat.bikecalc.core.common.Result
import dev.filinhat.bikecalc.core.model.pressure.PressureCalcParams
import dev.filinhat.bikecalc.core.presentation.BaseViewModel
import dev.filinhat.bikecalc.core.settings.SettingsStore
import dev.filinhat.bikecalc.feature.pressure.data.PressureSettings
import dev.filinhat.bikecalc.feature.pressure.domain.repository.PressureCalcRepository
import dev.filinhat.bikecalc.feature.pressure.domain.usecase.CalculatePressureUseCase
import dev.filinhat.bikecalc.feature.pressure.domain.usecase.DeleteAllResultsUseCase
import dev.filinhat.bikecalc.feature.pressure.domain.usecase.DeleteResultUseCase
import dev.filinhat.bikecalc.feature.pressure.domain.usecase.GetSavedResultsUseCase
import dev.filinhat.bikecalc.feature.pressure.presentation.state.PressureCalcAction
import dev.filinhat.bikecalc.feature.pressure.presentation.state.PressureCalcState
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel экрана расчета давления велосипеда.
 * Управляет состоянием UI, обработкой действий пользователя, расчетом давления
 * и сохранением результатов в базу данных.
 *
 * @property repository Репозиторий для работы с сохраненными результатами
 * @property calculatePressureUseCase UseCase для расчета давления
 * @property getSavedResultsUseCase UseCase для получения сохраненных результатов
 * @property deleteResultUseCase UseCase для удаления отдельного результата
 * @property deleteAllResultsUseCase UseCase для удаления всех результатов
 * @property settingsStore Хранилище настроек экрана
 */
class PressureCalculatorViewModel(
    private val repository: PressureCalcRepository,
    private val calculatePressureUseCase: CalculatePressureUseCase,
    private val getSavedResultsUseCase: GetSavedResultsUseCase,
    private val deleteResultUseCase: DeleteResultUseCase,
    private val deleteAllResultsUseCase: DeleteAllResultsUseCase,
    private val settingsStore: SettingsStore<PressureSettings>,
) : ViewModel(),
    BaseViewModel<PressureCalcState, PressureCalcAction> {
    private var observeSavedResultsJob: Job? = null

    private val _uiState = MutableStateFlow(PressureCalcState())
    override val uiState: StateFlow<PressureCalcState> =
        _uiState
            .onStart {
                observeSavedResults()
                loadSettings()
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.Lazily,
                initialValue = _uiState.value,
            )

    /**
     * Обрабатывает действия пользователя.
     *
     * @param event Действие для обработки
     */
    override fun onAction(event: PressureCalcAction) {
        when (event) {
            is PressureCalcAction.OnCalcPressure ->
                calcPressureResult(
                    PressureCalcParams(
                        riderWeight = event.riderWeight,
                        bikeWeight = event.bikeWeight,
                        wheelSize = event.wheelSize,
                        tireSize = event.tireSize,
                        weightUnit = event.weightUnit,
                        selectedTubeType = event.selectedTubeType,
                    ),
                )

            is PressureCalcAction.OnTabSelected -> {
                _uiState.update { it.copy(selectedTabIndex = event.index) }
            }

            is PressureCalcAction.OnTubeTypeChanged -> {
                _uiState.update { it.copy(selectedTubeType = event.tubeType) }
            }

            is PressureCalcAction.OnUpdateSettings -> {
                _uiState.update { it.copy(settings = event.settings) }
            }

            is PressureCalcAction.OnSaveSettings -> {
                saveSettings(event.settings)
            }

            is PressureCalcAction.OnDeleteResult -> {
                _uiState.update {
                    it.copy(showDeleteConfirmationForId = event.id)
                }
            }

            PressureCalcAction.OnConfirmDelete -> {
                val idToDelete = _uiState.value.showDeleteConfirmationForId
                if (idToDelete != null) {
                    viewModelScope.launch(Dispatchers.IO) {
                        deleteResultUseCase(idToDelete)
                    }
                }
                _uiState.update {
                    it.copy(showDeleteConfirmationForId = null)
                }
            }

            PressureCalcAction.OnDismissDeleteDialog -> {
                _uiState.update {
                    it.copy(showDeleteConfirmationForId = null)
                }
            }

            PressureCalcAction.OnDeleteAllResults -> {
                _uiState.update {
                    it.copy(showDeleteAllConfirmation = true)
                }
            }

            PressureCalcAction.OnConfirmDeleteAll -> {
                viewModelScope.launch(Dispatchers.IO) {
                    deleteAllResultsUseCase()
                }
                _uiState.update {
                    it.copy(showDeleteAllConfirmation = false)
                }
            }

            PressureCalcAction.OnDismissDeleteAllDialog -> {
                _uiState.update {
                    it.copy(showDeleteAllConfirmation = false)
                }
            }
        }
    }

    /**
     * Загружает сохраненные настройки из хранилища.
     * При ошибке загрузки используются дефолтные значения.
     */
    private fun loadSettings() {
        settingsStore
            .getSettings()
            .onEach { settings ->
                _uiState.update {
                    it.copy(
                        settings = settings,
                        selectedTubeType = settings.selectedTubeType,
                    )
                }
            }.catch { error ->
                // В случае ошибки используем дефолтные значения
            }.launchIn(viewModelScope)
    }

    /**
     * Сохраняет настройки в постоянное хранилище.
     *
     * @param settings Настройки для сохранения
     */
    private fun saveSettings(settings: PressureSettings) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                settingsStore.saveSettings(settings)
            } catch (error: Exception) {
                // Обработка ошибки сохранения
            }
        }
    }

    /**
     * Выполняет расчет давления на основе переданных параметров.
     * Сохраняет результат в базу данных при успешном расчете.
     *
     * @param params Параметры для расчета давления
     */
    private fun calcPressureResult(params: PressureCalcParams) {
        calculatePressureUseCase(params)
            .catch { throwable ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Ошибка расчета: ${throwable.message}",
                    )
                }
            }.onEach { result ->
                when (result) {
                    is Result.Success -> {
                        _uiState.update {
                            it.copy(
                                result = result.data,
                                isLoading = false,
                                errorMessage = null,
                            )
                        }

                        viewModelScope.launch(Dispatchers.IO) {
                            repository.saveCalcResult(
                                params = params,
                                frontPressure = result.data.frontPressure,
                                rearPressure = result.data.rearPressure,
                            )
                        }
                    }

                    is Result.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = "Ошибка при расчете давления",
                            )
                        }
                    }
                }
            }.flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    /**
     * Наблюдает за изменениями сохраненных результатов в базе данных.
     * Обновляет UI при изменении списка результатов.
     */
    private fun observeSavedResults() {
        observeSavedResultsJob?.cancel()
        observeSavedResultsJob =
            getSavedResultsUseCase()
                .catch { throwable ->
                    _uiState.update {
                        it.copy(
                            errorMessage = "Ошибка загрузки сохраненных результатов: ${throwable.message}",
                            isLoading = false,
                        )
                    }
                }.onEach { results ->
                    _uiState.update {
                        it.copy(
                            savedCalcResult = results.toImmutableList(),
                            isLoading = false,
                            errorMessage = null,
                        )
                    }
                }.flowOn(Dispatchers.IO)
                .launchIn(viewModelScope)
    }
}
