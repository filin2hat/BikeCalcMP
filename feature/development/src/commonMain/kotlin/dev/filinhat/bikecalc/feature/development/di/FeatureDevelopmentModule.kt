package dev.filinhat.bikecalc.feature.development.di

import dev.filinhat.bikecalc.core.settings.SettingsStore
import dev.filinhat.bikecalc.feature.development.data.DevelopmentSettings
import dev.filinhat.bikecalc.feature.development.data.createDevelopmentSettingsStore
import dev.filinhat.bikecalc.feature.development.viewmodel.DevelopmentCalculatorViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Koin модуль для feature development с универсальным механизмом настроек
 */
val featureDevelopmentModule = module {
    // Новая реализация с универсальным механизмом
    single<SettingsStore<DevelopmentSettings>>(qualifier = named("development")) { 
        createDevelopmentSettingsStore() 
    }

    // ViewModels
    viewModel { DevelopmentCalculatorViewModel(get(), get(qualifier = named("development"))) }
}
