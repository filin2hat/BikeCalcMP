package dev.filinhat.bikecalc.data.pressure.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import dev.filinhat.bikecalc.core.database.DatabaseFactory
import dev.filinhat.bikecalc.core.database.PressureResultsDatabase
import dev.filinhat.bikecalc.data.pressure.repository.PressureCalcRepositoryImpl
import dev.filinhat.bikecalc.data.pressure.service.PressureCalculationServiceImpl
import dev.filinhat.bikecalc.domain.pressure.repository.PressureCalcRepository
import dev.filinhat.bikecalc.domain.pressure.service.PressureCalculationService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * Koin модуль для data слоя расчета давления
 */
val dataPressureModule =
    module {
        // База данных
        single<PressureResultsDatabase> {
            get<DatabaseFactory>()
                .createDatabase()
                .setDriver(BundledSQLiteDriver())
                .build()
        }
        single { get<PressureResultsDatabase>().dao }

        // Сервисы
        singleOf(::PressureCalculationServiceImpl).bind<PressureCalculationService>()

        // Репозитории
        singleOf(::PressureCalcRepositoryImpl).bind<PressureCalcRepository>()
    }
