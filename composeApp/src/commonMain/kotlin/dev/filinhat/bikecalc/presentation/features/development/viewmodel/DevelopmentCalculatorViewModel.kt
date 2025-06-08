package dev.filinhat.bikecalc.presentation.features.development.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.filinhat.bikecalc.domain.model.DevelopmentCalcParams
import dev.filinhat.bikecalc.domain.repository.DevelopmentCalcRepository
import dev.filinhat.bikecalc.presentation.features.development.state.DevelopmentCalcAction
import dev.filinhat.bikecalc.presentation.features.development.state.DevelopmentCalcState
import dev.filinhat.bikecalc.presentation.util.BaseViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
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
    private val repository: DevelopmentCalcRepository,
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
            repository.calculateDevelopment(params).collect { results ->
                _uiState.update { it.copy(result = results.toImmutableList()) }
            }
        }
    }

    private fun clearResult() {
        _uiState.update { it.copy(result = persistentListOf()) }
    }
}
