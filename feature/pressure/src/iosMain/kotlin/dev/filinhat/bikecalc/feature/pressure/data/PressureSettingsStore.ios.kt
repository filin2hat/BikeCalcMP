package dev.filinhat.bikecalc.feature.pressure.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.json.Json
import platform.Foundation.NSUserDefaults
import platform.Foundation.setValue

/**
 * iOS-специфичная реализация PressureSettingsStore с использованием NSUserDefaults
 */
class IOSPressureSettingsStore(
    private val userDefaults: NSUserDefaults,
) : PressureSettingsStore {
    private val _settings = MutableStateFlow(PressureSettings())
    private val settingsKey = "pressure_settings"

    init {
        loadFromUserDefaults()
    }

    override fun getSettings(): Flow<PressureSettings> = _settings

    override suspend fun saveSettings(settings: PressureSettings) {
        _settings.value = settings
        saveToUserDefaults(settings)
    }

    private fun loadFromUserDefaults() {
        try {
            val jsonString = userDefaults.stringForKey(settingsKey)
            if (jsonString != null) {
                val settings = Json.decodeFromString(PressureSettings.serializer(), jsonString)
                _settings.value = settings
            }
        } catch (e: Exception) {
            // В случае ошибки используем дефолтные значения
        }
    }

    private fun saveToUserDefaults(settings: PressureSettings) {
        try {
            val jsonString = Json.encodeToString(PressureSettings.serializer(), settings)
            userDefaults.setValue(jsonString, settingsKey)
        } catch (e: Exception) {
            // Обработка ошибки сохранения
        }
    }
}

actual fun createPlatformPressureSettingsStore(): PressureSettingsStore {
    val userDefaults = NSUserDefaults.standardUserDefaults
    return IOSPressureSettingsStore(userDefaults)
}
