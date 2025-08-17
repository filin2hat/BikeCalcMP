package dev.filinhat.bikecalc.feature.development.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.json.Json
import java.io.File

/**
 * Desktop-специфичная реализация DevelopmentSettingsStore с хранением в файле
 */
class DesktopDevelopmentSettingsStore(
    private val file: File
) : DevelopmentSettingsStore {
    
    private val _settings = MutableStateFlow(DevelopmentSettings())
    
    init {
        loadFromFile()
    }
    
    override fun getSettings(): Flow<DevelopmentSettings> = _settings

    override suspend fun saveSettings(settings: DevelopmentSettings) {
        _settings.value = settings
        saveToFile(settings)
    }
    
    private fun loadFromFile() {
        try {
            if (file.exists()) {
                val jsonString = file.readText()
                val settings = Json.decodeFromString(DevelopmentSettings.serializer(), jsonString)
                _settings.value = settings
            }
        } catch (e: Exception) {
            // В случае ошибки используем дефолтные значения
        }
    }
    
    private fun saveToFile(settings: DevelopmentSettings) {
        try {
            val jsonString = Json.encodeToString(DevelopmentSettings.serializer(), settings)
            file.writeText(jsonString)
        } catch (e: Exception) {
            // Обработка ошибки сохранения
        }
    }
}

actual fun createPlatformDevelopmentSettingsStore(): DevelopmentSettingsStore {
    val userHome = System.getProperty("user.home")
    val settingsDir = File(userHome, ".bikecalc")
    if (!settingsDir.exists()) {
        settingsDir.mkdirs()
    }
    
    val settingsFile = File(settingsDir, "development_settings.json")
    return DesktopDevelopmentSettingsStore(settingsFile)
}
