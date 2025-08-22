package dev.filinhat.bikecalc.feature.pressure.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.json.Json

/**
 * Android-специфичная реализация PressureSettingsStore с использованием SharedPreferences
 */
class AndroidPressureSettingsStore(
    private val sharedPreferences: SharedPreferences,
) : PressureSettingsStore {
    private val _settings = MutableStateFlow(PressureSettings())
    private val settingsKey = "pressure_settings"

    init {
        loadFromSharedPreferences()
    }

    override fun getSettings(): Flow<PressureSettings> = _settings

    override suspend fun saveSettings(settings: PressureSettings) {
        _settings.value = settings
        saveToSharedPreferences(settings)
    }

    private fun loadFromSharedPreferences() {
        try {
            val jsonString = sharedPreferences.getString(settingsKey, null)
            if (jsonString != null) {
                val settings = Json.decodeFromString(PressureSettings.serializer(), jsonString)
                _settings.value = settings
            }
        } catch (e: Exception) {
            // В случае ошибки используем дефолтные значения
        }
    }

    private fun saveToSharedPreferences(settings: PressureSettings) {
        try {
            val jsonString = Json.encodeToString(PressureSettings.serializer(), settings)
            sharedPreferences.edit { putString(settingsKey, jsonString) }
        } catch (e: Exception) {
            // Обработка ошибки сохранения
        }
    }
}

/**
 * Функция для создания Android-реализации с Context
 */
fun createAndroidPressureSettingsStore(context: Context): PressureSettingsStore {
    val sharedPreferences =
        context.getSharedPreferences("pressure_settings", Context.MODE_PRIVATE)
    return AndroidPressureSettingsStore(sharedPreferences)
}

actual fun createPlatformPressureSettingsStore(): PressureSettingsStore {
    // В реальном приложении Context должен передаваться из DI
    // Пока используем заглушку, которая будет заменена в DI модуле
    throw UnsupportedOperationException("Context must be provided for Android implementation")
}
