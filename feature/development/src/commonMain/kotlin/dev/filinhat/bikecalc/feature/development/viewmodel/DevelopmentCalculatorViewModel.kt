package dev.filinhat.bikecalc.feature.development.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.filinhat.bikecalc.core.model.development.DevelopmentCalcParams
import dev.filinhat.bikecalc.core.presentation.BaseViewModel
import dev.filinhat.bikecalc.domain.development.usecase.CalculateDevelopmentUseCase
import dev.filinhat.bikecalc.feature.development.state.DevelopmentCalcAction
import dev.filinhat.bikecalc.feature.development.state.DevelopmentCalcState
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel для экрана расчёта развития метража.
 *
 * @property repository репозиторий для расчёта развития метража
 */
class DevelopmentCalculatorViewModel(
    private val calculateDevelopmentUseCase: CalculateDevelopmentUseCase,
) : ViewModel(),
    BaseViewModel<DevelopmentCalcState, DevelopmentCalcAction> {
    private val _uiState = MutableStateFlow(DevelopmentCalcState())
    override val uiState: StateFlow<DevelopmentCalcState> =
        _uiState.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = _uiState.value,
        )

    override fun onAction(event: DevelopmentCalcAction) =
        when (event) {
            is DevelopmentCalcAction.OnCalculateDevelopment -> calculateDevelopment(event.params)
            is DevelopmentCalcAction.OnClearResult -> clearResult()
        }

    private fun calculateDevelopment(params: DevelopmentCalcParams) {
        viewModelScope.launch(Dispatchers.IO) {
            calculateDevelopmentUseCase(params).collect { results ->
                _uiState.update { it.copy(result = results.toImmutableList()) }
            }
        }
    }

    private fun clearResult() {
        _uiState.update { it.copy(result = persistentListOf()) }
    }
}