package dev.filinhat.bikecalc.feature.pressure.state

import dev.filinhat.bikecalc.core.enums.tube.TubeType
import dev.filinhat.bikecalc.core.model.PressureCalcResult
import dev.filinhat.bikecalc.core.model.SavedPressureCalcResult
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

/**
 * Определяет состояния UI, которые может принимать ViewModel.
 */
data class PressureCalcState(
    /**
     * Состояние успешного получения данных
     *
     * @param result Результат расчета давления
     * @param selectedTabIndex Индекс выбранного таба
     * @param isLoading Признак загрузки
     * @param errorMessage Текст ошибки
     * @param savedCalcResult Список сохраненных результатов расчета
     * @param selectedTubeType Выбранный тип покрышки
     * @param showDeleteConfirmationForId ID элемента для подтверждения удаления
     * @param showDeleteAllConfirmation Признак показа диалога подтверждения удаления всех записей
     */
    val result: PressureCalcResult = PressureCalcResult(),
    val selectedTabIndex: Int = 0,
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val savedCalcResult: ImmutableList<SavedPressureCalcResult> = persistentListOf(),
    val selectedTubeType: TubeType = TubeType.TUBES,
    val showDeleteConfirmationForId: Long? = null,
    val showDeleteAllConfirmation: Boolean = false,
)

