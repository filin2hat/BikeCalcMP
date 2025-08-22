package dev.filinhat.bikecalc.designsystem.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.json.Json
import platform.Foundation.NSUserDefaults
import platform.Foundation.setValue

/**
 * iOS-специфичная реализация ThemeSettingsStore с использованием NSUserDefaults
 */
class IOSThemeSettingsStore(
    private val userDefaults: NSUserDefaults,
) : ThemeSettingsStore {
    private val _settings = MutableStateFlow(ThemeSettings())
    private val settingsKey = "theme_settings"

    init {
        loadFromUserDefaults()
    }

    override fun getSettings(): Flow<ThemeSettings> = _settings

    override suspend fun saveSettings(settings: ThemeSettings) {
        _settings.value = settings
        saveToUserDefaults(settings)
    }

    private fun loadFromUserDefaults() {
        try {
            val jsonString = userDefaults.stringForKey(settingsKey)
            if (jsonString != null) {
                val settings = Json.decodeFromString(ThemeSettings.serializer(), jsonString)
                _settings.value = settings
            }
        } catch (e: Exception) {
            // В случае ошибки используем дефолтные значения
        }
    }

    private fun saveToUserDefaults(settings: ThemeSettings) {
        try {
            val jsonString = Json.encodeToString(ThemeSettings.serializer(), settings)
            userDefaults.setValue(jsonString, settingsKey)
        } catch (e: Exception) {
            // Обработка ошибки сохранения
        }
    }
}

actual fun createPlatformThemeSettingsStore(): ThemeSettingsStore {
    val userDefaults = NSUserDefaults.standardUserDefaults
    return IOSThemeSettingsStore(userDefaults)
}
