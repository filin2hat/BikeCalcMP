package dev.filinhat.bikecalc.feature.development.di

import android.content.Context
import dev.filinhat.bikecalc.feature.development.data.DevelopmentSettingsStore
import dev.filinhat.bikecalc.feature.development.data.createAndroidDevelopmentSettingsStore
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Android-специфичный Koin модуль для feature development
 */
val featureDevelopmentAndroidModule: Module = module {
    // Переопределяем DevelopmentSettingsStore для Android с Context
    single<DevelopmentSettingsStore> { 
        createAndroidDevelopmentSettingsStore(get<Context>())
    }
}
