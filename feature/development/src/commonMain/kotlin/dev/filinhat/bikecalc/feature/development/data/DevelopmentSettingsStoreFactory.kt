package dev.filinhat.bikecalc.feature.development.data

/**
 * Фабрика для создания DevelopmentSettingsStore
 */
fun createDevelopmentSettingsStore(): DevelopmentSettingsStore {
    return createPlatformDevelopmentSettingsStore()
}
