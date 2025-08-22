package dev.filinhat.bikecalc.designsystem.di

import android.content.Context
import dev.filinhat.bikecalc.designsystem.data.ThemeSettingsStore
import dev.filinhat.bikecalc.designsystem.data.createAndroidThemeSettingsStore
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Android-специфичный Koin модуль для designsystem
 */
val designSystemAndroidModule: Module =
    module {
        single<ThemeSettingsStore> {
            createAndroidThemeSettingsStore(get<Context>())
        }
    }
