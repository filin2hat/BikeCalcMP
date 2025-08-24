package dev.filinhat.bikecalc.core.settings

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import java.io.File

/**
 * Desktop-специфичная реализация SettingsStore с хранением в файле
 */
class DesktopSettingsStore<T>(
    private val qualifier: String,
    private val defaultValue: T,
    private val serializer: KSerializer<T>
) : SettingsStore<T> {
    
    private val _settings = MutableStateFlow(defaultValue)
    private val settingsFile = File(
        System.getProperty("user.home"), 
        ".bikecalc_${qualifier}.json"
    )
    
    init {
        loadFromFile()
    }
    
    override fun getSettings(): Flow<T> = _settings.asStateFlow()
    
    override suspend fun saveSettings(settings: T) {
        try {
            _settings.value = settings
            saveToFile(settings)
        } catch (e: Exception) {
            throw SettingsError.SerializationError(
                message = "Ошибка сохранения настроек: ${e.message}",
                cause = e
            )
        }
    }
    
    private fun loadFromFile() {
        try {
            if (settingsFile.exists() && settingsFile.canRead()) {
                val jsonString = settingsFile.readText()
                if (jsonString.isNotBlank()) {
                    val settings = Json.decodeFromString(serializer, jsonString)
                    _settings.value = settings
                }
            }
        } catch (e: Exception) {
            // Graceful fallback к default значению при ошибке загрузки
            _settings.value = defaultValue
        }
    }
    
    private fun saveToFile(settings: T) {
        try {
            // Создаем родительскую директорию если не существует
            settingsFile.parentFile?.mkdirs()
            
            val jsonString = Json.encodeToString(serializer, settings)
            settingsFile.writeText(jsonString)
        } catch (e: Exception) {
            throw SettingsError.StorageError(
                message = "Ошибка записи в файл ${settingsFile.absolutePath}: ${e.message}",
                cause = e
            )
        }
    }
}

/**
 * Платформо-специфичная фабрика для Desktop
 */
actual fun <T> createPlatformSettingsStore(
    qualifier: String,
    defaultValue: T,
    serializer: KSerializer<T>
): SettingsStore<T> = DesktopSettingsStore(
    qualifier = qualifier,
    defaultValue = defaultValue,
    serializer = serializer
)
