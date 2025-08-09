package dev.filinhat.bikecalc.domain.pressure.di

import dev.filinhat.bikecalc.domain.pressure.usecase.CalculatePressureUseCase
import dev.filinhat.bikecalc.domain.pressure.usecase.DeleteAllResultsUseCase
import dev.filinhat.bikecalc.domain.pressure.usecase.DeleteResultUseCase
import dev.filinhat.bikecalc.domain.pressure.usecase.GetSavedResultsUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

/**
 * Koin модуль для доменного слоя расчета давления
 */
val domainPressureModule =
    module {
        // Use Cases
        singleOf(::CalculatePressureUseCase)
        singleOf(::GetSavedResultsUseCase)
        singleOf(::DeleteResultUseCase)
        singleOf(::DeleteAllResultsUseCase)
    }

