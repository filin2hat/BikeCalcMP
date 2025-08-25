package dev.filinhat.bikecalc.core.settings

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

/**
 * Android-специфичная реализация SettingsStore с использованием SharedPreferences
 */
class AndroidSettingsStore<T>(
    private val sharedPreferences: SharedPreferences,
    private val qualifier: String,
    private val defaultValue: T,
    private val serializer: KSerializer<T>,
) : SettingsStore<T> {
    private val _settings = MutableStateFlow(defaultValue)

    init {
        loadFromSharedPreferences()
    }

    override fun getSettings(): Flow<T> = _settings.asStateFlow()

    override suspend fun saveSettings(settings: T) {
        try {
            _settings.value = settings
            saveToSharedPreferences(settings)
        } catch (e: Exception) {
            throw SettingsError.SerializationError(
                message = "Ошибка сохранения настроек: ${e.message}",
                cause = e,
            )
        }
    }

    private fun loadFromSharedPreferences() {
        try {
            val jsonString = sharedPreferences.getString(qualifier, null)
            if (jsonString != null) {
                val settings = Json.decodeFromString(serializer, jsonString)
                _settings.value = settings
            }
        } catch (e: Exception) {
            // Graceful fallback к default значению при ошибке загрузки
            _settings.value = defaultValue
        }
    }

    private fun saveToSharedPreferences(settings: T) {
        try {
            val jsonString = Json.encodeToString(serializer, settings)
            sharedPreferences.edit {
                putString(qualifier, jsonString)
            }
        } catch (e: Exception) {
            throw SettingsError.StorageError(
                message = "Ошибка записи в SharedPreferences: ${e.message}",
                cause = e,
            )
        }
    }
}

/**
 * Функция для создания Android-реализации с Context из Koin DI
 */
fun <T> createAndroidSettingsStore(
    context: Context,
    qualifier: String,
    defaultValue: T,
    serializer: KSerializer<T>,
): SettingsStore<T> {
    val sharedPreferences = context.getSharedPreferences(qualifier, Context.MODE_PRIVATE)
    return AndroidSettingsStore(
        sharedPreferences = sharedPreferences,
        qualifier = qualifier,
        defaultValue = defaultValue,
        serializer = serializer,
    )
}

/**
 * Платформо-специфичная фабрика для Android с автоматическим получением Context из DI
 */
actual fun <T> createPlatformSettingsStore(
    qualifier: String,
    defaultValue: T,
    serializer: KSerializer<T>,
): SettingsStore<T> =
    object : KoinComponent {
        val context: Context = get()
    }.let { koinComponent ->
        createAndroidSettingsStore(
            context = koinComponent.context,
            qualifier = qualifier,
            defaultValue = defaultValue,
            serializer = serializer,
        )
    }
