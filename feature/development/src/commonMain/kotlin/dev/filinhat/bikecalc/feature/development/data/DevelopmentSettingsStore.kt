package dev.filinhat.bikecalc.feature.development.data

import kotlinx.coroutines.flow.Flow

/**
 * Интерфейс для сохранения и восстановления настроек экрана развития метража
 */
interface DevelopmentSettingsStore {
    /**
     * Получить сохраненные настройки
     */
    fun getSettings(): Flow<DevelopmentSettings>

    /**
     * Сохранить настройки
     */
    suspend fun saveSettings(settings: DevelopmentSettings)
}

/**
 * Expect функция для создания платформо-специфичного хранилища
 */
expect fun createPlatformDevelopmentSettingsStore(): DevelopmentSettingsStore
