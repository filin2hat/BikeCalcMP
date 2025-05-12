package dev.filinhat.bikecalc.presentation.screen.pressure

import dev.filinhat.bikecalc.domain.enums.tire.TireSize
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
     */
    data class OnCalcPressure(
        val riderWeight: Double,
        val bikeWeight: Double,
        val wheelSize: WheelSize,
        val tireSize: TireSize,
        val weightUnit: WeightUnit,
    ) : PressureCalcAction

    /**
     * Выбор вкладки.
     *
     * @property index Индекс выбранной вкладки.
     */
    data class OnTabSelected(
        val index: Int,
    ) : PressureCalcAction
}
