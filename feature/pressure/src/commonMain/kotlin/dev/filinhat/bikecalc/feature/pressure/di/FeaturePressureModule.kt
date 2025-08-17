package dev.filinhat.bikecalc.feature.pressure.di

import dev.filinhat.bikecalc.feature.pressure.data.PressureSettingsStore
import dev.filinhat.bikecalc.feature.pressure.data.createPlatformPressureSettingsStore
import dev.filinhat.bikecalc.feature.pressure.viewmodel.PressureCalculatorViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin модуль для feature pressure
 */
val featurePressureModule =
    module {
        single<PressureSettingsStore> { createPlatformPressureSettingsStore() }

        // ViewModels
        viewModel {
            PressureCalculatorViewModel(
                repository = get(),
                calculatePressureUseCase = get(),
                getSavedResultsUseCase = get(),
                deleteResultUseCase = get(),
                deleteAllResultsUseCase = get(),
                settingsStore = get(),
            )
        }
    }
