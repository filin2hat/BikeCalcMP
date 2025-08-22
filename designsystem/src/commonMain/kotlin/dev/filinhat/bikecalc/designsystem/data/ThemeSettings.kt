package dev.filinhat.bikecalc.designsystem.data

import kotlinx.serialization.Serializable

/**
 * Модель данных для сохранения настроек темы
 */
@Serializable
data class ThemeSettings(
    val isDarkMode: Boolean = false,
)
