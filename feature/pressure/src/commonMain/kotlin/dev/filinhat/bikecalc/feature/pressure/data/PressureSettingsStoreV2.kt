package dev.filinhat.bikecalc.feature.pressure.data

import dev.filinhat.bikecalc.core.settings.SettingsStore
import dev.filinhat.bikecalc.core.settings.createSettingsStore

/**
 * Фабрика для создания PressureSettingsStore используя универсальный механизм
 */
fun createPressureSettingsStoreV2(): SettingsStore<PressureSettings> = createSettingsStore(
    qualifier = "pressure_settings", // Тот же ключ что и раньше для совместимости!
    defaultValue = PressureSettings()
)

