package dev.filinhat.bikecalc.domain.pressure.di

import dev.filinhat.bikecalc.domain.pressure.usecase.CalculatePressureUseCase
import dev.filinhat.bikecalc.domain.pressure.usecase.CalculatePressureUseCaseImpl
import dev.filinhat.bikecalc.domain.pressure.usecase.DeleteAllResultsUseCase
import dev.filinhat.bikecalc.domain.pressure.usecase.DeleteAllResultsUseCaseImpl
import dev.filinhat.bikecalc.domain.pressure.usecase.DeleteResultUseCase
import dev.filinhat.bikecalc.domain.pressure.usecase.DeleteResultUseCaseImpl
import dev.filinhat.bikecalc.domain.pressure.usecase.GetSavedResultsUseCase
import dev.filinhat.bikecalc.domain.pressure.usecase.GetSavedResultsUseCaseImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

/**
 * Koin модуль для доменного слоя расчета давления
 */
val domainPressureModule =
    module {
        // Use Cases bindings: interface -> impl
        single<CalculatePressureUseCase> { CalculatePressureUseCaseImpl(get()) }
        single<GetSavedResultsUseCase> { GetSavedResultsUseCaseImpl(get()) }
        single<DeleteResultUseCase> { DeleteResultUseCaseImpl(get()) }
        single<DeleteAllResultsUseCase> { DeleteAllResultsUseCaseImpl(get()) }
    }

