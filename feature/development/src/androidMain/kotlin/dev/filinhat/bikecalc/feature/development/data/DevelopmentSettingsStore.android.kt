package dev.filinhat.bikecalc.feature.development.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.json.Json

/**
 * Android-специфичная реализация DevelopmentSettingsStore с использованием SharedPreferences
 */
class AndroidDevelopmentSettingsStore(
    private val sharedPreferences: SharedPreferences,
) : DevelopmentSettingsStore {
    private val _settings = MutableStateFlow(DevelopmentSettings())
    private val settingsKey = "development_settings"

    init {
        loadFromSharedPreferences()
    }

    override fun getSettings(): Flow<DevelopmentSettings> = _settings

    override suspend fun saveSettings(settings: DevelopmentSettings) {
        _settings.value = settings
        saveToSharedPreferences(settings)
    }

    private fun loadFromSharedPreferences() {
        try {
            val jsonString = sharedPreferences.getString(settingsKey, null)
            if (jsonString != null) {
                val settings = Json.decodeFromString(DevelopmentSettings.serializer(), jsonString)
                _settings.value = settings
            }
        } catch (e: Exception) {
            // В случае ошибки используем дефолтные значения
        }
    }

    private fun saveToSharedPreferences(settings: DevelopmentSettings) {
        try {
            val jsonString = Json.encodeToString(DevelopmentSettings.serializer(), settings)
            sharedPreferences.edit { putString(settingsKey, jsonString) }
        } catch (e: Exception) {
            // Обработка ошибки сохранения
        }
    }
}

/**
 * Функция для создания Android-реализации с Context
 */
fun createAndroidDevelopmentSettingsStore(context: Context): DevelopmentSettingsStore {
    val sharedPreferences =
        context.getSharedPreferences("development_settings", Context.MODE_PRIVATE)
    return AndroidDevelopmentSettingsStore(sharedPreferences)
}

actual fun createPlatformDevelopmentSettingsStore(): DevelopmentSettingsStore {
    // В реальном приложении Context должен передаваться из DI
    // Пока используем заглушку, которая будет заменена в DI модуле
    throw UnsupportedOperationException("Context must be provided for Android implementation")
}
