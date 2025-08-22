package dev.filinhat.bikecalc.designsystem.data

import kotlinx.coroutines.flow.Flow

/**
 * Интерфейс для сохранения и восстановления настроек темы
 */
interface ThemeSettingsStore {
    /**
     * Получить сохраненные настройки темы
     */
    fun getSettings(): Flow<ThemeSettings>

    /**
     * Сохранить настройки темы
     */
    suspend fun saveSettings(settings: ThemeSettings)
}

/**
 * Expect функция для создания платформо-специфичного хранилища
 */
expect fun createPlatformThemeSettingsStore(): ThemeSettingsStore
