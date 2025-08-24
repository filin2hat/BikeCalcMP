package dev.filinhat.bikecalc.designsystem.data

import dev.filinhat.bikecalc.core.settings.SettingsStore
import dev.filinhat.bikecalc.core.settings.createSettingsStore

/**
 * Фабрика для создания ThemeSettingsStore используя универсальный механизм
 */
fun createThemeSettingsStore(): SettingsStore<ThemeSettings> = createSettingsStore(
    qualifier = "theme_settings", // Тот же ключ что и раньше для совместимости!
    defaultValue = ThemeSettings()
)
