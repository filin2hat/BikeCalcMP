package dev.filinhat.bikecalc.feature.pressure.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Desktop-специфичная реализация PressureSettingsStore с хранением в памяти
 * (временная реализация без файловой системы для совместимости с Kotlin Multiplatform)
 */
class DesktopPressureSettingsStore : PressureSettingsStore {
    private val _settings = MutableStateFlow(PressureSettings())

    override fun getSettings(): Flow<PressureSettings> = _settings

    override suspend fun saveSettings(settings: PressureSettings) {
        _settings.value = settings
    }
}

actual fun createPlatformPressureSettingsStore(): PressureSettingsStore = DesktopPressureSettingsStore()
