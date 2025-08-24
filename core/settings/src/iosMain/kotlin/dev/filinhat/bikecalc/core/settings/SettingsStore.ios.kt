package dev.filinhat.bikecalc.core.settings

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import platform.Foundation.NSUserDefaults
import platform.Foundation.setValue

/**
 * iOS-специфичная реализация SettingsStore с использованием NSUserDefaults
 */
class IOSSettingsStore<T>(
    private val userDefaults: NSUserDefaults,
    private val qualifier: String,
    private val defaultValue: T,
    private val serializer: KSerializer<T>
) : SettingsStore<T> {
    
    private val _settings = MutableStateFlow(defaultValue)
    
    init {
        loadFromUserDefaults()
    }
    
    override fun getSettings(): Flow<T> = _settings.asStateFlow()
    
    override suspend fun saveSettings(settings: T) {
        try {
            _settings.value = settings
            saveToUserDefaults(settings)
        } catch (e: Exception) {
            throw SettingsError.SerializationError(
                message = "Ошибка сохранения настроек: ${e.message}",
                cause = e
            )
        }
    }
    
    private fun loadFromUserDefaults() {
        try {
            val jsonString = userDefaults.stringForKey(qualifier)
            if (jsonString != null && jsonString.isNotBlank()) {
                val settings = Json.decodeFromString(serializer, jsonString)
                _settings.value = settings
            }
        } catch (e: Exception) {
            // Graceful fallback к default значению при ошибке загрузки
            _settings.value = defaultValue
        }
    }
    
    private fun saveToUserDefaults(settings: T) {
        try {
            val jsonString = Json.encodeToString(serializer, settings)
            userDefaults.setValue(jsonString, qualifier)
            userDefaults.synchronize() // Принудительная синхронизация
        } catch (e: Exception) {
            throw SettingsError.StorageError(
                message = "Ошибка записи в NSUserDefaults: ${e.message}",
                cause = e
            )
        }
    }
}

/**
 * Платформо-специфичная фабрика для iOS
 */
actual fun <T> createPlatformSettingsStore(
    qualifier: String,
    defaultValue: T,
    serializer: KSerializer<T>
): SettingsStore<T> {
    val userDefaults = NSUserDefaults.standardUserDefaults
    return IOSSettingsStore(
        userDefaults = userDefaults,
        qualifier = qualifier,
        defaultValue = defaultValue,
        serializer = serializer
    )
}
