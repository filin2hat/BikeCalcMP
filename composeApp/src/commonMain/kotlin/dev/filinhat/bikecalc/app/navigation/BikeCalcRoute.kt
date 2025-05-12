package dev.filinhat.bikecalc.app.navigation

import kotlinx.serialization.Serializable

/**
 *  Список маршрутов приложения BikeCalcMP.
 */
sealed interface BikeCalcRoute {
    @Serializable
    data object PressureGraph : BikeCalcRoute

    @Serializable
    data object PressureCalculator : BikeCalcRoute
}
