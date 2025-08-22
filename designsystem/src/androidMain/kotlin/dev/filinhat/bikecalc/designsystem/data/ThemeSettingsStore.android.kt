package dev.filinhat.bikecalc.designsystem.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.json.Json

/**
 * Android-специфичная реализация ThemeSettingsStore с использованием SharedPreferences
 */
class AndroidThemeSettingsStore(
    private val sharedPreferences: SharedPreferences,
) : ThemeSettingsStore {
    private val _settings = MutableStateFlow(ThemeSettings())
    private val settingsKey = "theme_settings"

    init {
        loadFromSharedPreferences()
    }

    override fun getSettings(): Flow<ThemeSettings> = _settings

    override suspend fun saveSettings(settings: ThemeSettings) {
        _settings.value = settings
        saveToSharedPreferences(settings)
    }

    private fun loadFromSharedPreferences() {
        try {
            val jsonString = sharedPreferences.getString(settingsKey, null)
            println("AndroidThemeSettingsStore: Загружаем из SharedPreferences: $jsonString")
            if (jsonString != null) {
                val settings = Json.decodeFromString<ThemeSettings>(jsonString)
                println("AndroidThemeSettingsStore: Успешно декодировали настройки: isDarkMode = ${settings.isDarkMode}")
                _settings.value = settings
            } else {
                println("AndroidThemeSettingsStore: Нет сохраненных настроек, используем дефолтные")
            }
        } catch (e: Exception) {
            println("AndroidThemeSettingsStore: Ошибка загрузки настроек: ${e.message}")
            // В случае ошибки используем дефолтные значения
        }
    }

    private fun saveToSharedPreferences(settings: ThemeSettings) {
        try {
            val jsonString = Json.encodeToString(settings)
            println("AndroidThemeSettingsStore: Сохраняем в SharedPreferences: $jsonString")
            sharedPreferences.edit { putString(settingsKey, jsonString) }
            println("AndroidThemeSettingsStore: Успешно сохранили в SharedPreferences")
        } catch (e: Exception) {
            println("AndroidThemeSettingsStore: Ошибка сохранения в SharedPreferences: ${e.message}")
            // Обработка ошибки сохранения
        }
    }
}

/**
 * Функция для создания Android-реализации с Context
 */
fun createAndroidThemeSettingsStore(context: Context): ThemeSettingsStore {
    val sharedPreferences =
        context.getSharedPreferences("theme_settings", Context.MODE_PRIVATE)
    return AndroidThemeSettingsStore(sharedPreferences)
}

actual fun createPlatformThemeSettingsStore(): ThemeSettingsStore {
    // В реальном приложении Context должен передаваться из DI
    // Пока используем заглушку, которая будет заменена в DI модуле
    throw UnsupportedOperationException("Context must be provided for Android implementation")
}
