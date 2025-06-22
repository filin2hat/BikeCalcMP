package dev.filinhat.bikecalc.app.navigation

import kotlinx.serialization.Serializable

/**
 *  Список маршрутов приложения BikeCalcMP.
 */
sealed interface BikeCalcRoute {
    /**
     * Маршрут экрана расчета давления.
     */
    @Serializable
    data object PressureCalculator : BikeCalcRoute

    /**
     * Маршрут экрана расчета развития метража.
     */
    @Serializable
    data object DevelopmentCalculator : BikeCalcRoute
}
