package dev.filinhat.bikecalc.domain.development.di

import dev.filinhat.bikecalc.domain.development.usecase.CalculateDevelopmentUseCase
import dev.filinhat.bikecalc.domain.development.usecase.CalculateDevelopmentUseCaseImpl
import org.koin.dsl.module

/**
 * Koin модуль для доменного слоя расчёта развития метража
 */
val domainDevelopmentModule =
    module {
        single<CalculateDevelopmentUseCase> { CalculateDevelopmentUseCaseImpl(get()) }
    }
