package dev.filinhat.bikecalc.di

import dev.filinhat.bikecalc.data.pressure.di.dataPressureModule
import dev.filinhat.bikecalc.domain.pressure.di.domainPressureModule
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
            dataPressureModule,
            domainPressureModule,
            featurePressureModule,
        )
    }

/**
 * Модуль, содержащий зависимости, специфичные для платформы
 */
expect val platformModule: Module
