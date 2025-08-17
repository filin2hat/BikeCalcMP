package dev.filinhat.bikecalc.feature.pressure.data

import kotlinx.coroutines.flow.Flow

/**
 * Интерфейс для сохранения и восстановления настроек экрана расчета давления
 */
interface PressureSettingsStore {
    /**
     * Получить сохраненные настройки
     */
    fun getSettings(): Flow<PressureSettings>

    /**
     * Сохранить настройки
     */
    suspend fun saveSettings(settings: PressureSettings)
}

/**
 * Expect функция для создания платформо-специфичного хранилища
 */
expect fun createPlatformPressureSettingsStore(): PressureSettingsStore
