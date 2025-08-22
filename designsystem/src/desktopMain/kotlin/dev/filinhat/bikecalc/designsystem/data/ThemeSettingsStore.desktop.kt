package dev.filinhat.bikecalc.designsystem.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.json.Json
import java.io.File

/**
 * Desktop-специфичная реализация ThemeSettingsStore с хранением в файле
 */
class DesktopThemeSettingsStore : ThemeSettingsStore {
    private val _settings = MutableStateFlow(ThemeSettings())
    private val settingsFile =
        File(System.getProperty("user.home"), ".bikecalc_theme_settings.json")

    init {
        loadFromFile()
    }

    override fun getSettings(): Flow<ThemeSettings> = _settings

    override suspend fun saveSettings(settings: ThemeSettings) {
        println("DesktopThemeSettingsStore: Сохраняем настройки темы: isDarkMode = ${settings.isDarkMode}")
        _settings.value = settings
        saveToFile(settings)
        println("DesktopThemeSettingsStore: Настройки темы успешно сохранены в файл")
    }

    private fun loadFromFile() {
        try {
            if (settingsFile.exists()) {
                val jsonString = settingsFile.readText()
                println("DesktopThemeSettingsStore: Загружаем из файла: $jsonString")
                val settings = Json.decodeFromString<ThemeSettings>(jsonString)
                println("DesktopThemeSettingsStore: Успешно декодировали настройки: isDarkMode = ${settings.isDarkMode}")
                _settings.value = settings
            } else {
                println("DesktopThemeSettingsStore: Файл настроек не найден, используем дефолтные")
            }
        } catch (e: Exception) {
            println("DesktopThemeSettingsStore: Ошибка загрузки настроек: ${e.message}")
        }
    }

    private fun saveToFile(settings: ThemeSettings) {
        try {
            val jsonString = Json.encodeToString(settings)
            println("DesktopThemeSettingsStore: Сохраняем в файл: $jsonString")
            settingsFile.writeText(jsonString)
            println("DesktopThemeSettingsStore: Успешно сохранили в файл")
        } catch (e: Exception) {
            println("DesktopThemeSettingsStore: Ошибка сохранения в файл: ${e.message}")
        }
    }
}

actual fun createPlatformThemeSettingsStore(): ThemeSettingsStore = DesktopThemeSettingsStore()
