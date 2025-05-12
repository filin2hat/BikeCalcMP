package dev.filinhat.bikecalc.app.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import dev.filinhat.bikecalc.presentation.screen.pressure.PressureCalculatorScreenRoot
import dev.filinhat.bikecalc.presentation.screen.pressure.PressureCalculatorViewModel
import org.koin.compose.viewmodel.koinViewModel

/**
 * Навигационный граф приложения BikeCalcMP.
 */
@Composable
fun BikeCalcNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = BikeCalcRoute.PressureGraph,
    ) {
        navigation<BikeCalcRoute.PressureGraph>(startDestination = BikeCalcRoute.PressureCalculator) {
            composable<BikeCalcRoute.PressureCalculator>(
                exitTransition = { slideOutHorizontally() },
                popEnterTransition = { slideInHorizontally() },
            ) {
                val viewModel = koinViewModel<PressureCalculatorViewModel>()
                PressureCalculatorScreenRoot(viewModel)
            }
        }
    }
}
