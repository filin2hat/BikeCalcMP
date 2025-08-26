package dev.filinhat.bikecalc.feature.pressure.data

import dev.filinhat.bikecalc.core.settings.SettingsStore
import dev.filinhat.bikecalc.core.settings.createSettingsStore

/**
 * Фабрика для создания хранилища настроек экрана расчета давления.
 * Использует универсальный механизм настроек с уникальным идентификатором.
 *
 * @return Настроенное хранилище настроек для экрана расчета давления
 */
fun createPressureSettingsStore(): SettingsStore<PressureSettings> =
    createSettingsStore(
        qualifier = "pressure_settings",
        defaultValue = PressureSettings(),
    )
