package dev.filinhat.bikecalc.presentation.features.pressure.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.filinhat.bikecalc.domain.model.PressureCalcParams
import dev.filinhat.bikecalc.domain.repository.PressureCalcRepository
import dev.filinhat.bikecalc.presentation.features.pressure.state.PressureCalcAction
import dev.filinhat.bikecalc.presentation.features.pressure.state.PressureCalcState
import dev.filinhat.bikecalc.presentation.util.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel экрана [PressureCalculatorScreen]
 *
 * @property repository - репозиторий для работы с данными расчета давления.
 */
class PressureCalculatorViewModel(
    private val repository: PressureCalcRepository,
) : ViewModel(),
    BaseViewModel<PressureCalcState, PressureCalcAction> {
    private var observeSavedResultsJob: Job? = null

    private val _uiState = MutableStateFlow<PressureCalcState>(PressureCalcState())
    override val uiState =
        _uiState
            .onStart { observeSavedResults() }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Companion.Lazily,
                initialValue = _uiState.value,
            )

    override fun onAction(event: PressureCalcAction) =
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

            is PressureCalcAction.OnTabSelected ->
                _uiState.update { state ->
                    state.copy(selectedTabIndex = event.index)
                }

            is PressureCalcAction.OnTubeTypeChanged ->
                _uiState.update { state ->
                    state.copy(selectedTubeType = event.tubeType)
                }

            is PressureCalcAction.OnDeleteAllResults -> deleteAllResults()
        }

    private fun observeSavedResults() {
        observeSavedResultsJob?.cancel()
        observeSavedResultsJob =
            repository
                .getAllResults()
                .flowOn(Dispatchers.IO)
                .onEach { savedResults ->
                    _uiState.update { it.copy(savedCalcResult = savedResults) }
                }.launchIn(viewModelScope)
    }

    private fun calcPressureResult(params: PressureCalcParams) {
        viewModelScope.launch(Dispatchers.IO) {
            repository
                .calcPressure(params)
                .catch { e ->
                }.collect { result ->
                    _uiState.update { state ->
                        state.copy(result = result)
                    }
                }
        }
    }

    private fun deleteAllResults() {
        viewModelScope.launch {
            repository.deleteAllResults()
        }
    }
}
