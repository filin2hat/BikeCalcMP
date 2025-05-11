package dev.filinhat.bikecalc.di

import dev.filinhat.bikecalc.data.repository.PressureCalcRepositoryImpl
import dev.filinhat.bikecalc.domain.repository.PressureCalcRepository
import dev.filinhat.bikecalc.presentation.screen.pressure.PressureCalculatorViewModel
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
    }
