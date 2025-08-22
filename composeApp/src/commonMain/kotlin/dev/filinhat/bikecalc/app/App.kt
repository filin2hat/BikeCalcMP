package dev.filinhat.bikecalc.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import dev.filinhat.bikecalc.designsystem.theme.BikeCalcTheme
import dev.filinhat.bikecalc.designsystem.viewmodel.ThemeViewModel
import dev.filinhat.bikecalc.presentation.MainScreen
import org.koin.compose.viewmodel.koinViewModel

/**
 * Точка входа в приложение
 */
@Composable
fun App() {
    val themeViewModel: ThemeViewModel = koinViewModel()

    BikeCalcTheme(themeViewModel = themeViewModel) {
        NoFontScalingContent {
            MainScreen(themeViewModel = themeViewModel)
        }
    }
}

/**
 * Отключает масштабирование шрифтов
 */
@Composable
fun NoFontScalingContent(content: @Composable () -> Unit) {
    val currentDensity = LocalDensity.current
    CompositionLocalProvider(
        LocalDensity provides Density(currentDensity.density, fontScale = 1.0f),
    ) {
        content()
    }
}
