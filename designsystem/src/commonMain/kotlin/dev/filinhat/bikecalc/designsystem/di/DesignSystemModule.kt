package dev.filinhat.bikecalc.designsystem.di

import dev.filinhat.bikecalc.designsystem.data.ThemeSettingsStore
import dev.filinhat.bikecalc.designsystem.data.createPlatformThemeSettingsStore
import dev.filinhat.bikecalc.designsystem.viewmodel.ThemeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin модуль для designsystem
 */
val designSystemModule =
    module {
        single<ThemeSettingsStore> { createPlatformThemeSettingsStore() }

        // ViewModels
        viewModel { ThemeViewModel(get()) }
    }
