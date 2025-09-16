package dev.filinhat.bikecalc.feature.pressure.presentation.state

import dev.filinhat.bikecalc.core.enums.tube.TubeType
import dev.filinhat.bikecalc.core.model.pressure.PressureCalcResult
import dev.filinhat.bikecalc.core.model.pressure.SavedPressureCalcResult
import dev.filinhat.bikecalc.feature.pressure.data.PressureSettings
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

/**
 * Определяет состояния UI, которые может принимать ViewModel.
 * Содержит все данные, необходимые для отображения экрана расчета давления.
 *
 * @property result Результат расчета давления (давление переднего и заднего колеса)
 * @property selectedTabIndex Индекс выбранного таба (0 - новый расчет, 1 - предыдущие результаты)
 * @property isLoading Признак загрузки данных или выполнения операции
 * @property errorMessage Текст ошибки для отображения пользователю
 * @property savedCalcResult Список сохраненных результатов расчета давления
 * @property selectedTubeType Выбранный тип монтажа покрышки (камерная/бескамерная)
 * @property showDeleteConfirmationForId ID элемента для подтверждения удаления (null если диалог не показан)
 * @property showDeleteAllConfirmation Признак показа диалога подтверждения удаления всех записей
 * @property settings Текущие настройки формы (вес, размеры, единицы измерения)
 */
data class PressureCalcState(
    val result: PressureCalcResult = PressureCalcResult(),
    val selectedTabIndex: Int = 0,
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val savedCalcResult: ImmutableList<SavedPressureCalcResult> = persistentListOf(),
    val selectedTubeType: TubeType = TubeType.TUBES,
    val showDeleteConfirmationForId: Long? = null,
    val showDeleteAllConfirmation: Boolean = false,
    val settings: PressureSettings = PressureSettings(),
)
