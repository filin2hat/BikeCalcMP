package dev.filinhat.bikecalc.presentation.screen.pressure

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.filinhat.bikecalc.domain.enums.tire.TireSize
import dev.filinhat.bikecalc.domain.enums.tube.TubeType
import dev.filinhat.bikecalc.domain.enums.unit.WeightUnit
import dev.filinhat.bikecalc.domain.enums.wheel.WheelSize
import dev.filinhat.bikecalc.domain.repository.PressureCalcRepository
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
                started = SharingStarted.Lazily,
                initialValue = _uiState.value,
            )

    override fun onAction(event: PressureCalcAction) =
        when (event) {
            is PressureCalcAction.OnCalcPressure ->
                calcPressureResult(
                    event.riderWeight,
                    event.bikeWeight,
                    event.wheelSize,
                    event.tireSize,
                    event.weightUnit,
                    event.selectedTubeType,
                )

            is PressureCalcAction.OnTabSelected ->
                _uiState.update { state ->
                    state.copy(selectedTabIndex = event.index)
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

    private fun calcPressureResult(
        riderWeight: Double,
        bikeWeight: Double,
        wheelSize: WheelSize,
        tireSize: TireSize,
        weightUnit: WeightUnit,
        selectedTubeType: TubeType,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repository
                .calcPressure(
                    riderWeight,
                    bikeWeight,
                    wheelSize,
                    tireSize,
                    weightUnit,
                    selectedTubeType,
                ).catch { e ->
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
