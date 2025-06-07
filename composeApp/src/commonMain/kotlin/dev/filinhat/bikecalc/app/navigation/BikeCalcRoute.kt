package dev.filinhat.bikecalc.app.navigation

import kotlinx.serialization.Serializable

/**
 *  Список маршрутов приложения BikeCalcMP.
 */
sealed interface BikeCalcRoute {
    /**
     * Главный граф навигации приложения.
     */
    @Serializable
    data object PressureGraph : BikeCalcRoute

    /**
     * Маршрут экрана расчета давления.
     */
    @Serializable
    data object PressureCalculator : BikeCalcRoute
}
