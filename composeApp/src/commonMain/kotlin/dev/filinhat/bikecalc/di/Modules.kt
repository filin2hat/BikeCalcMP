package dev.filinhat.bikecalc.di

import dev.filinhat.bikecalc.designsystem.di.designSystemModule
import dev.filinhat.bikecalc.feature.development.di.featureDevelopmentModule
import dev.filinhat.bikecalc.feature.pressure.di.featurePressureModule
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Модуль, содержащий зависимости, которые не зависят от платформы
 */
val sharedModule =
    module {
        // Подключаем модули других слоев
        includes(
            featureDevelopmentModule,
            featurePressureModule,
            designSystemModule,
        )
    }

/**
 * Модуль, содержащий зависимости, специфичные для платформы
 */
expect val platformModule: Module
