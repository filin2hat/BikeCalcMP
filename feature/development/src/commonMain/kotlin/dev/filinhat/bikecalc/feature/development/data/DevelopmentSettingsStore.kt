package dev.filinhat.bikecalc.feature.development.data

import dev.filinhat.bikecalc.core.settings.SettingsStore
import dev.filinhat.bikecalc.core.settings.createSettingsStore

/**
 * Фабрика для создания DevelopmentSettingsStore используя универсальный механизм
 */
fun createDevelopmentSettingsStore(): SettingsStore<DevelopmentSettings> =
    createSettingsStore(
        qualifier = "development_settings",
        defaultValue = DevelopmentSettings(),
    )
