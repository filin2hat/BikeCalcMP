package dev.filinhat.bikecalc.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.filinhat.bikecalc.presentation.features.pressure.PressureCalculatorScreenRoot
import dev.filinhat.bikecalc.presentation.features.pressure.viewmodel.PressureCalculatorViewModel
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
    }
}
