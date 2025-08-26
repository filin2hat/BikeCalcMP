package dev.filinhat.bikecalc.feature.development.data

import dev.filinhat.bikecalc.core.settings.SettingsStore
import dev.filinhat.bikecalc.core.settings.createSettingsStore

/**
 * Фабрика для создания хранилища настроек экрана развития метража.
 * Использует универсальный механизм настроек с уникальным идентификатором.
 *
 * @return Настроенное хранилище настроек для экрана развития метража
 */
fun createDevelopmentSettingsStore(): SettingsStore<DevelopmentSettings> =
    createSettingsStore(
        qualifier = "development_settings",
        defaultValue = DevelopmentSettings(),
    )
