package dev.filinhat.bikecalc.feature.pressure.state

import dev.filinhat.bikecalc.core.enums.tire.TireSize
import dev.filinhat.bikecalc.core.enums.tube.TubeType
import dev.filinhat.bikecalc.core.enums.unit.WeightUnit
import dev.filinhat.bikecalc.core.enums.wheel.WheelSize
import dev.filinhat.bikecalc.feature.pressure.data.PressureSettings

/**
 * Определяет события UI, инициированные пользователем,
 * на которые ViewModel должен отреагировать.
 * Включает все возможные действия на экране расчета давления.
 */
sealed interface PressureCalcAction {
    /**
     * Событие "Рассчитать давление".
     * Запускает процесс расчета давления на основе введенных параметров.
     *
     * @param bikeWeight Вес велосипеда в выбранных единицах измерения
     * @param riderWeight Вес велосипедиста в выбранных единицах измерения
     * @param wheelSize Размер колеса велосипеда
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
     * Выбор вкладки в интерфейсе.
     * Переключает между вкладками "Новый расчет" и "Предыдущие результаты".
     *
     * @property index Индекс выбранной вкладки (0 - новый расчет, 1 - предыдущие результаты)
     */
    data class OnTabSelected(
        val index: Int,
    ) : PressureCalcAction

    /**
     * Изменение типа монтажа покрышки.
     * Переключает между камерной и бескамерной сборкой.
     *
     * @property tubeType Выбранный тип монтажа покрышки
     */
    data class OnTubeTypeChanged(
        val tubeType: TubeType,
    ) : PressureCalcAction

    /**
     * Обновление настроек формы в UI.
     * Изменяет отображаемые настройки без сохранения в постоянное хранилище.
     *
     * @property settings Новые настройки для отображения
     */
    data class OnUpdateSettings(
        val settings: PressureSettings,
    ) : PressureCalcAction

    /**
     * Сохранение настроек формы в постоянное хранилище.
     * Сохраняет настройки для последующего использования.
     *
     * @property settings Настройки для сохранения
     */
    data class OnSaveSettings(
        val settings: PressureSettings,
    ) : PressureCalcAction

    /**
     * Инициирует удаление отдельного результата (показывает диалог подтверждения).
     *
     * @property id ID результата для удаления
     */
    data class OnDeleteResult(
        val id: Long,
    ) : PressureCalcAction

    /**
     * Подтверждает удаление отдельного результата.
     * Выполняется после подтверждения в диалоге.
     */
    data object OnConfirmDelete : PressureCalcAction

    /**
     * Отклоняет удаление отдельного результата (закрывает диалог).
     * Отменяет операцию удаления.
     */
    data object OnDismissDeleteDialog : PressureCalcAction

    /**
     * Инициирует удаление всех результатов (показывает диалог подтверждения).
     * Запрашивает подтверждение для очистки всех сохраненных результатов.
     */
    data object OnDeleteAllResults : PressureCalcAction

    /**
     * Подтверждает удаление всех результатов.
     * Выполняется после подтверждения в диалоге.
     */
    data object OnConfirmDeleteAll : PressureCalcAction

    /**
     * Отклоняет удаление всех результатов (закрывает диалог).
     * Отменяет операцию удаления всех результатов.
     */
    data object OnDismissDeleteAllDialog : PressureCalcAction
}
