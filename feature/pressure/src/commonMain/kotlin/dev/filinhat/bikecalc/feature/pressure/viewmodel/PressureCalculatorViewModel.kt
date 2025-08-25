package dev.filinhat.bikecalc.feature.pressure.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.filinhat.bikecalc.core.common.Result
import dev.filinhat.bikecalc.core.model.pressure.PressureCalcParams
import dev.filinhat.bikecalc.core.presentation.BaseViewModel
import dev.filinhat.bikecalc.core.settings.SettingsStore
import dev.filinhat.bikecalc.domain.pressure.repository.PressureCalcRepository
import dev.filinhat.bikecalc.domain.pressure.usecase.CalculatePressureUseCase
import dev.filinhat.bikecalc.domain.pressure.usecase.DeleteAllResultsUseCase
import dev.filinhat.bikecalc.domain.pressure.usecase.DeleteResultUseCase
import dev.filinhat.bikecalc.domain.pressure.usecase.GetSavedResultsUseCase
import dev.filinhat.bikecalc.feature.pressure.data.PressureSettings
import dev.filinhat.bikecalc.feature.pressure.state.PressureCalcAction
import dev.filinhat.bikecalc.feature.pressure.state.PressureCalcState
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
 * ViewModel экрана PressureCalculatorScreen
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

    private fun saveSettings(settings: PressureSettings) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                settingsStore.saveSettings(settings)
            } catch (error: Exception) {
                // Обработка ошибки сохранения
            }
        }
    }

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
