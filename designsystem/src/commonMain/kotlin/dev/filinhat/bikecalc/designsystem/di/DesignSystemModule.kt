package dev.filinhat.bikecalc.designsystem.di

import dev.filinhat.bikecalc.core.settings.SettingsStore
import dev.filinhat.bikecalc.designsystem.data.ThemeSettings
import dev.filinhat.bikecalc.designsystem.data.createThemeSettingsStore
import dev.filinhat.bikecalc.designsystem.viewmodel.ThemeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Koin модуль для designsystem с универсальным механизмом настроек
 */
val designSystemModule =
    module {
        single<SettingsStore<ThemeSettings>>(qualifier = named("theme")) {
            createThemeSettingsStore()
        }

        viewModel { ThemeViewModel(get(qualifier = named("theme"))) }
    }
