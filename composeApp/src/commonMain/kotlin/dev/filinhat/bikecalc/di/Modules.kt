package dev.filinhat.bikecalc.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import dev.filinhat.bikecalc.data.database.DatabaseFactory
import dev.filinhat.bikecalc.data.database.PressureResultsDatabase
import dev.filinhat.bikecalc.data.repository.PressureCalcRepositoryImpl
import dev.filinhat.bikecalc.data.service.PressureCalculationServiceImpl
import dev.filinhat.bikecalc.domain.repository.PressureCalcRepository
import dev.filinhat.bikecalc.domain.service.PressureCalculationService
import dev.filinhat.bikecalc.presentation.features.pressure.viewmodel.PressureCalculatorViewModel
import dev.filinhat.bikecalc.data.service.DevelopmentCalculationServiceImpl
import dev.filinhat.bikecalc.domain.service.DevelopmentCalculationService
import dev.filinhat.bikecalc.data.repository.DevelopmentCalcRepositoryImpl
import dev.filinhat.bikecalc.domain.repository.DevelopmentCalcRepository
import dev.filinhat.bikecalc.presentation.features.development.viewmodel.DevelopmentCalculatorViewModel
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
        // Сервисы
        singleOf(::PressureCalculationServiceImpl).bind<PressureCalculationService>()
        singleOf(::DevelopmentCalculationServiceImpl).bind<DevelopmentCalculationService>()

        // Репозитории
        singleOf(::PressureCalcRepositoryImpl).bind<PressureCalcRepository>()
        singleOf(::DevelopmentCalcRepositoryImpl).bind<DevelopmentCalcRepository>()

        // Вьюмодели
        viewModelOf(::PressureCalculatorViewModel)
        viewModelOf(::DevelopmentCalculatorViewModel)

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
