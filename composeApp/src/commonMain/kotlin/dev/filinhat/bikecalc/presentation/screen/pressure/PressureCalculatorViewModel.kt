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
import kotlinx.coroutines.launch

/**
 * ViewModel экрана [PressureCalculatorScreen]
 */
class PressureCalculatorViewModel(
    private val repository: PressureCalcRepository,
) : ViewModel(),
    BaseViewModel<UiState, UiEvent> {
    private val _uiState = MutableStateFlow<UiState>(UiState.Success(PressureCalcResult()))
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
            is UiEvent.CalcPressure ->
                onCalcPressure(
                    event.bikeWeight,
                    event.riderWeight,
                    event.wheelSize,
                    event.tireSize,
                    event.weightUnit,
                )
        }

    private fun onCalcPressure(
        riderWeight: Double,
        bikeWeight: Double,
        wheelSize: WheelSize,
        tireSize: TireSize,
        weightUnit: WeightUnit,
    ) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository
                .calcPressure(riderWeight, bikeWeight, wheelSize, tireSize, weightUnit)
                .catch { e ->
                    _uiState.value = UiState.Error("Указаны не корректные данные для расчета.")
                }.collect { result ->
                    _uiState.value = UiState.Success(result)
                }
        }
    }
}
