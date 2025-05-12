package dev.filinhat.bikecalc.presentation.screen.pressure

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.filinhat.bikecalc.domain.enums.tire.TireSize
import dev.filinhat.bikecalc.domain.enums.unit.WeightUnit
import dev.filinhat.bikecalc.domain.enums.wheel.WheelSize
import dev.filinhat.bikecalc.domain.model.PressureCalcResult
import dev.filinhat.bikecalc.domain.repository.PressureCalcRepository
import dev.filinhat.bikecalc.presentation.util.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel экрана [PressureCalculatorScreen]
 */
class PressureCalculatorViewModel(
    private val repository: PressureCalcRepository,
) : ViewModel(),
    BaseViewModel<UiState, UiEvent> {
    private val _uiState = MutableStateFlow(initState)
    override val uiState =
        _uiState
            .asStateFlow()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Lazily,
                initialValue = UiState.Loading,
            )

    override fun perform(event: UiEvent) =
        when (event) {
            is UiEvent.OnCalcPressure ->
                onCalcPressure(
                    event.bikeWeight,
                    event.riderWeight,
                    event.wheelSize,
                    event.tireSize,
                    event.weightUnit,
                )

            is UiEvent.OnTabSelected ->
                _uiState.update { state ->
                    state.copy(selectedTabIndex = event.index)
                }
        }

    private fun onCalcPressure(
        riderWeight: Double,
        bikeWeight: Double,
        wheelSize: WheelSize,
        tireSize: TireSize,
        weightUnit: WeightUnit,
    ) {
        viewModelScope.launch {
            repository
                .calcPressure(riderWeight, bikeWeight, wheelSize, tireSize, weightUnit)
                .catch { e ->
                }.collect { result ->
                    _uiState.value = UiState.Success(result)
                }
        }
    }

    companion object {
        val initState = UiState.Success(PressureCalcResult(), selectedTabIndex = 0)
    }
}
