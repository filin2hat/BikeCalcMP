package dev.filinhat.bikecalc.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import dev.filinhat.bikecalc.data.database.DatabaseFactory
import dev.filinhat.bikecalc.data.database.PressureResultsDatabase
import dev.filinhat.bikecalc.data.repository.PressureCalcRepositoryImpl
import dev.filinhat.bikecalc.domain.repository.PressureCalcRepository
import dev.filinhat.bikecalc.presentation.screen.pressure.PressureCalculatorViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * Модуль, содержащий зависимости, которые не зависят от платформы
 */
val sharedModule =
    module {
        // Репозитории
        singleOf(::PressureCalcRepositoryImpl).bind<PressureCalcRepository>()

        // Вьюмодели
        viewModelOf(::PressureCalculatorViewModel)

        // База данных
        single {
            get<DatabaseFactory>()
                .createDatabase()
                .setDriver(BundledSQLiteDriver())
                .build()
        }
        single { get<PressureResultsDatabase>().dao }
    }

/**
 * Модуль, содержащий зависимости, специфичные для платформы
 */
expect val platformModule: Module
