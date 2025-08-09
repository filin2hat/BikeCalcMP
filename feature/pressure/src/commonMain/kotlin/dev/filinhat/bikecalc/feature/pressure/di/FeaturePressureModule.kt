package dev.filinhat.bikecalc.feature.pressure.di

import dev.filinhat.bikecalc.feature.pressure.viewmodel.PressureCalculatorViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin модуль для feature pressure
 */
val featurePressureModule =
    module {
        // ViewModels
        viewModel { PressureCalculatorViewModel(get(), get(), get(), get(), get()) }
    }
