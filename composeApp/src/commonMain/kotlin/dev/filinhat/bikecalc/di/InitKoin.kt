package dev.filinhat.bikecalc.di

import io.kotzilla.sdk.analytics.koin.analytics
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

/**
 * Функция инициализации Koin и его модулей.
 */
fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(sharedModule, platformModule)
        try {
            analytics()
        } catch (e: NotImplementedError) {
            println("Analytics not supported on this platform: ${e.message}")
        }
    }
}
