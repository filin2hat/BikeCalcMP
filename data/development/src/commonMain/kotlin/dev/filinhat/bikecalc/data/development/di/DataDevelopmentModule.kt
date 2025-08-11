package dev.filinhat.bikecalc.data.development.di

import dev.filinhat.bikecalc.data.development.repository.DevelopmentCalcRepositoryImpl
import dev.filinhat.bikecalc.data.development.service.DevelopmentCalculationServiceImpl
import dev.filinhat.bikecalc.domain.development.repository.DevelopmentCalcRepository
import dev.filinhat.bikecalc.domain.development.service.DevelopmentCalculationService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * Koin модуль для data слоя расчета давления
 */
val dataDevelopmentModule =
    module {

        // Сервисы
        singleOf(::DevelopmentCalculationServiceImpl).bind<DevelopmentCalculationService>()

        // Репозитории
        singleOf(::DevelopmentCalcRepositoryImpl).bind<DevelopmentCalcRepository>()
    }
