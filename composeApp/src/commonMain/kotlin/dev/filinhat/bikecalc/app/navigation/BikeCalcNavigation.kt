package dev.filinhat.bikecalc.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.filinhat.bikecalc.feature.development.screen.DevelopmentCalculatorScreenRoot
import dev.filinhat.bikecalc.feature.development.viewmodel.DevelopmentCalculatorViewModel
import dev.filinhat.bikecalc.feature.pressure.screen.PressureCalculatorScreenRoot
import dev.filinhat.bikecalc.feature.pressure.viewmodel.PressureCalculatorViewModel
import org.koin.compose.viewmodel.koinViewModel

/**
 * Навигационный граф приложения BikeCalcMP.
 */
@Composable
fun BikeCalcNavigation(
    navController: NavHostController,
    startDestination: BikeCalcRoute,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable<BikeCalcRoute.PressureCalculator> {
            val viewModel = koinViewModel<PressureCalculatorViewModel>()
            PressureCalculatorScreenRoot(viewModel, keyboardController, focusManager)
        }
        composable<BikeCalcRoute.DevelopmentCalculator> {
            val viewModel = koinViewModel<DevelopmentCalculatorViewModel>()
            DevelopmentCalculatorScreenRoot(viewModel, keyboardController, focusManager)
        }
    }
}
