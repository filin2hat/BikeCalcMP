package dev.filinhat.bikecalc.core.settings

import kotlinx.coroutines.flow.flowOf
import kotlinx.serialization.Serializable
import kotlin.test.Test
import kotlin.test.assertEquals

@Serializable
data class TestSettings(
    val value: String = "default",
    val number: Int = 0
)

/**
 * Базовые тесты для SettingsStore
 */
class SettingsStoreTest {
    
    @Test
    fun `test_settings_store_interface_exists`() {
        // Простая проверка что интерфейс существует и компилируется
        val store = createMockSettingsStore(TestSettings("test", 42))
        assertEquals("test", store.getCurrentSettings().value)
        assertEquals(42, store.getCurrentSettings().number)
    }
}

/**
 * Упрощенная mock реализация для тестирования
 */
private fun createMockSettingsStore(defaultValue: TestSettings): MockSettingsStore {
    return MockSettingsStore(defaultValue)
}

private class MockSettingsStore(private val defaultValue: TestSettings) : SettingsStore<TestSettings> {
    private var currentSettings = defaultValue
    
    fun getCurrentSettings() = currentSettings
    
    override fun getSettings() = flowOf(currentSettings)
    
    override suspend fun saveSettings(settings: TestSettings) {
        currentSettings = settings
    }
}
