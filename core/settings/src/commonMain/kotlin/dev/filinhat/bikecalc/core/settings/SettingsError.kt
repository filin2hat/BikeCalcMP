package dev.filinhat.bikecalc.core.settings

/**
 * Базовый класс для ошибок системы настроек
 */
sealed class SettingsError : Exception() {
    /**
     * Ошибка сериализации данных
     */
    data class SerializationError(
        override val message: String,
        override val cause: Throwable?,
    ) : SettingsError()

    /**
     * Ошибка доступа к платформенному хранилищу
     */
    data class StorageError(
        override val message: String,
        override val cause: Throwable?,
    ) : SettingsError()

    /**
     * Ошибка валидации данных
     */
    data class ValidationError(
        override val message: String,
    ) : SettingsError()
}
