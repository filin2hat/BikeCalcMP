package dev.filinhat.bikecalc.core.settings

import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.KSerializer

/**
 * Универсальный интерфейс для сохранения и восстановления настроек приложения
 * с поддержкой любых @Serializable data классов
 *
 * @param T Тип данных настроек, должен быть @Serializable
 */
interface SettingsStore<T> {
    /**
     * Получить поток настроек с реактивными обновлениями
     *
     * @return Flow с текущими настройками и всеми последующими изменениями
     */
    fun getSettings(): Flow<T>

    /**
     * Сохранить настройки асинхронно
     *
     * @param settings Новые настройки для сохранения
     */
    suspend fun saveSettings(settings: T)
}

/**
 * Expect функция для создания платформо-специфичного хранилища настроек
 *
 * @param qualifier Уникальный идентификатор для различения типов настроек
 * @param defaultValue Значение по умолчанию для настроек
 * @param serializer Сериализатор для данного типа настроек
 * @return Платформо-специфичная реализация SettingsStore
 */
expect fun <T> createPlatformSettingsStore(
    qualifier: String,
    defaultValue: T,
    serializer: KSerializer<T>,
): SettingsStore<T>
