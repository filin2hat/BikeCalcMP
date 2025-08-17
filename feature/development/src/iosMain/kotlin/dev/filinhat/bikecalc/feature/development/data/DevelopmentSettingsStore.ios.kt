package dev.filinhat.bikecalc.feature.development.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.json.Json
import platform.Foundation.NSUserDefaults
import platform.Foundation.setValue

/**
 * iOS-специфичная реализация DevelopmentSettingsStore с использованием NSUserDefaults
 */
class IOSDevelopmentSettingsStore(
    private val userDefaults: NSUserDefaults,
) : DevelopmentSettingsStore {
    private val _settings = MutableStateFlow(DevelopmentSettings())
    private val settingsKey = "development_settings"

    init {
        loadFromUserDefaults()
    }

    override fun getSettings(): Flow<DevelopmentSettings> = _settings

    override suspend fun saveSettings(settings: DevelopmentSettings) {
        _settings.value = settings
        saveToUserDefaults(settings)
    }

    private fun loadFromUserDefaults() {
        try {
            val jsonString = userDefaults.stringForKey(settingsKey)
            if (jsonString != null) {
                val settings = Json.decodeFromString(DevelopmentSettings.serializer(), jsonString)
                _settings.value = settings
            }
        } catch (e: Exception) {
            // В случае ошибки используем дефолтные значения
        }
    }

    private fun saveToUserDefaults(settings: DevelopmentSettings) {
        try {
            val jsonString = Json.encodeToString(DevelopmentSettings.serializer(), settings)
            userDefaults.setValue(jsonString, settingsKey)
        } catch (e: Exception) {
            // Обработка ошибки сохранения
        }
    }
}

actual fun createPlatformDevelopmentSettingsStore(): DevelopmentSettingsStore {
    val userDefaults = NSUserDefaults.standardUserDefaults
    return IOSDevelopmentSettingsStore(userDefaults)
}
