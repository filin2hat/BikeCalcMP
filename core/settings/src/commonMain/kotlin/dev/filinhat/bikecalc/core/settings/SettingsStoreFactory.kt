package dev.filinhat.bikecalc.core.settings

import kotlinx.serialization.serializer

/**
 * Convenience функция для создания SettingsStore с автоматическим определением serializer
 * 
 * @param qualifier Уникальный идентификатор для различения типов настроек
 * @param defaultValue Значение по умолчанию для настроек
 * @return Типизированная реализация SettingsStore для данного типа
 */
inline fun <reified T> createSettingsStore(
    qualifier: String,
    defaultValue: T
): SettingsStore<T> = createPlatformSettingsStore(
    qualifier = qualifier,
    defaultValue = defaultValue,
    serializer = serializer<T>()
)
