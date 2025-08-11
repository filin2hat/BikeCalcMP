package dev.filinhat.bikecalc.domain.development.di

import dev.filinhat.bikecalc.domain.development.usecase.CalculateDevelopmentUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

/**
 * Koin модуль для доменного слоя расчёта развития метража
 */
val domainDevelopmentModule =
    module {
        // Use Cases
        singleOf(::CalculateDevelopmentUseCase)
    }






