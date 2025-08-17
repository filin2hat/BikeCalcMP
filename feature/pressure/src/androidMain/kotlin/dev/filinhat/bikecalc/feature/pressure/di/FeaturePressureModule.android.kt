package dev.filinhat.bikecalc.feature.pressure.di

import android.content.Context
import dev.filinhat.bikecalc.feature.pressure.data.PressureSettingsStore
import dev.filinhat.bikecalc.feature.pressure.data.createAndroidPressureSettingsStore
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Android-специфичный Koin модуль для feature pressure
 */
val featurePressureAndroidModule: Module =
    module {
        single<PressureSettingsStore> {
            createAndroidPressureSettingsStore(get<Context>())
        }
    }
