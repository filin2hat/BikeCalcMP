package dev.filinhat.bikecalc.feature.development.di

import dev.filinhat.bikecalc.feature.development.data.DevelopmentSettingsStore
import dev.filinhat.bikecalc.feature.development.data.createPlatformDevelopmentSettingsStore
import dev.filinhat.bikecalc.feature.development.viewmodel.DevelopmentCalculatorViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin модуль для feature development
 */
val featureDevelopmentModule =
    module {
        single<DevelopmentSettingsStore> { createPlatformDevelopmentSettingsStore() }

        // ViewModels
        viewModel { DevelopmentCalculatorViewModel(get(), get()) }
    }
