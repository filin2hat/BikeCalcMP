package dev.filinhat.bikecalc.di

import io.kotzilla.generated.monitoring
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
            monitoring()
            println("✅ Kotzilla monitoring успешно инициализирован")
        } catch (e: NotImplementedError) {
            println("⚠️ Monitoring not supported on this platform: ${e.message}")
        } catch (e: Exception) {
            println("❌ Ошибка инициализации Kotzilla monitoring: ${e.message}")
            e.printStackTrace()
        }
    }
}
