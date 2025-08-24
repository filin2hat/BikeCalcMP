package dev.filinhat.bikecalc.designsystem.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.filinhat.bikecalc.core.settings.SettingsStore
import dev.filinhat.bikecalc.designsystem.data.ThemeSettings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * ViewModel для управления темой приложения с универсальным SettingsStore
 */
class ThemeViewModel(
    private val themeSettingsStore: SettingsStore<ThemeSettings>,
) : ViewModel() {
    private val _isDarkMode = MutableStateFlow(false) // Будет обновлено при загрузке настроек
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    init {
        println("ThemeViewModel: Инициализация ViewModel с универсальным SettingsStore")
        loadThemeSettings()
    }

    /**
     * Переключить тему
     */
    fun toggleTheme() {
        val newValue = !_isDarkMode.value
        println("ThemeViewModel: Переключаем тему на ${if (newValue) "темную" else "светлую"}")
        _isDarkMode.value = newValue
        saveThemeSettings(ThemeSettings(isDarkMode = newValue))
    }

    /**
     * Установить темную тему
     */
    fun setDarkMode(isDark: Boolean) {
        _isDarkMode.value = isDark
        saveThemeSettings(ThemeSettings(isDarkMode = isDark))
    }

    private fun loadThemeSettings() {
        themeSettingsStore
            .getSettings()
            .onEach { settings ->
                println("ThemeViewModel: Загружены настройки темы через универсальный store: isDarkMode = ${settings.isDarkMode}")
                _isDarkMode.value = settings.isDarkMode
            }.catch { error ->
                println("ThemeViewModel: Ошибка загрузки настроек темы: ${error.message}")
                // В случае ошибки оставляем дефолтное значение (false)
            }.launchIn(viewModelScope)
    }

    private fun saveThemeSettings(settings: ThemeSettings) {
        viewModelScope.launch {
            try {
                println("ThemeViewModel: Сохраняем настройки темы через универсальный store: isDarkMode = ${settings.isDarkMode}")
                themeSettingsStore.saveSettings(settings)
                println("ThemeViewModel: Настройки темы успешно сохранены")
            } catch (e: Exception) {
                println("ThemeViewModel: Ошибка сохранения настроек темы: ${e.message}")
            }
        }
    }
}
