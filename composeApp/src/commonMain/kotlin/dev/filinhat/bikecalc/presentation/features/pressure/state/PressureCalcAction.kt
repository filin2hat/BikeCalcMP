package dev.filinhat.bikecalc.presentation.features.pressure.state

import dev.filinhat.bikecalc.domain.enums.tire.TireSize
import dev.filinhat.bikecalc.domain.enums.tube.TubeType
import dev.filinhat.bikecalc.domain.enums.unit.WeightUnit
import dev.filinhat.bikecalc.domain.enums.wheel.WheelSize

/**
 * Определяет события UI, инициированные пользователем,
 * на которые ViewModel должен отреагировать.
 */
sealed interface PressureCalcAction {
    /**
     * Событие "Рассчитать давление"
     *
     * @param bikeWeight Вес велосипеда
     * @param riderWeight Вес велосипедиста
     * @param wheelSize Размер колеса
     * @param tireSize Размер покрышки
     * @param weightUnit Единица измерения веса (кг или фунт)
     * @param selectedTubeType Бескамерная / камерная сборка покрышек
     */
    data class OnCalcPressure(
        val riderWeight: Double,
        val bikeWeight: Double,
        val wheelSize: WheelSize,
        val tireSize: TireSize,
        val weightUnit: WeightUnit,
        val selectedTubeType: TubeType,
    ) : PressureCalcAction

    /**
     * Выбор вкладки.
     *
     * @property index Индекс выбранной вкладки.
     */
    data class OnTabSelected(
        val index: Int,
    ) : PressureCalcAction

    /**
     * Изменение типа покрышки.
     *
     * @property tubeType Выбранный тип покрышки.
     */
    data class OnTubeTypeChanged(
        val tubeType: TubeType,
    ) : PressureCalcAction

    /**
     * Инициирует удаление результата (показывает диалог).
     *
     * @property id ID результата для удаления.
     */
    data class OnDeleteResult(
        val id: Long,
    ) : PressureCalcAction

    /**
     * Подтверждает удаление результата.
     */
    data object OnConfirmDelete : PressureCalcAction

    /**
     * Отклоняет удаление результата (закрывает диалог).
     */
    data object OnDismissDeleteDialog : PressureCalcAction

    /**
     * Инициирует удаление всех результатов (показывает диалог).
     */
    data object OnDeleteAllResults : PressureCalcAction

    /**
     * Подтверждает удаление всех результатов.
     */
    data object OnConfirmDeleteAll : PressureCalcAction

    /**
     * Отклоняет удаление всех результатов (закрывает диалог).
     */
    data object OnDismissDeleteAllDialog : PressureCalcAction
}
