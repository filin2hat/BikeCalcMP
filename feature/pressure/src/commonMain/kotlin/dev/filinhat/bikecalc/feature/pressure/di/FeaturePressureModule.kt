package dev.filinhat.bikecalc.feature.pressure.di

import dev.filinhat.bikecalc.core.settings.SettingsStore
import dev.filinhat.bikecalc.feature.pressure.data.PressureSettings
import dev.filinhat.bikecalc.feature.pressure.data.createPressureSettingsStore
import dev.filinhat.bikecalc.feature.pressure.viewmodel.PressureCalculatorViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Koin модуль для фичи pressure с универсальным механизмом настроек.
 * Предоставляет зависимости для экрана расчета давления велосипеда.
 */
val featurePressureModule =
    module {
        single<SettingsStore<PressureSettings>>(qualifier = named("pressure")) {
            createPressureSettingsStore()
        }

        // ViewModels
        viewModel {
            PressureCalculatorViewModel(
                repository = get(),
                calculatePressureUseCase = get(),
                getSavedResultsUseCase = get(),
                deleteResultUseCase = get(),
                deleteAllResultsUseCase = get(),
                settingsStore = get(qualifier = named("pressure")),
            )
        }
    }
