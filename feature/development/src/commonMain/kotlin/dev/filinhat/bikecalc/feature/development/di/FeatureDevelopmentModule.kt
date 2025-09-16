package dev.filinhat.bikecalc.feature.development.di

import dev.filinhat.bikecalc.core.settings.SettingsStore
import dev.filinhat.bikecalc.feature.development.data.DevelopmentSettings
import dev.filinhat.bikecalc.feature.development.data.createDevelopmentSettingsStore
import dev.filinhat.bikecalc.feature.development.data.repository.DevelopmentCalcRepositoryImpl
import dev.filinhat.bikecalc.feature.development.data.service.DevelopmentCalculationServiceImpl
import dev.filinhat.bikecalc.feature.development.domain.repository.DevelopmentCalcRepository
import dev.filinhat.bikecalc.feature.development.domain.service.DevelopmentCalculationService
import dev.filinhat.bikecalc.feature.development.domain.usecase.CalculateDevelopmentUseCase
import dev.filinhat.bikecalc.feature.development.domain.usecase.CalculateDevelopmentUseCaseImpl
import dev.filinhat.bikecalc.feature.development.presentation.viewmodel.DevelopmentCalculatorViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Koin модуль для фичи development с универсальным механизмом настроек.
 * Предоставляет зависимости для экрана расчёта развития метража.
 */
val featureDevelopmentModule =
    module {
        single<SettingsStore<DevelopmentSettings>>(qualifier = named("development")) {
            createDevelopmentSettingsStore()
        }

        // Services
        factory<DevelopmentCalculationService> { DevelopmentCalculationServiceImpl() }

        // Repositories
        factory<DevelopmentCalcRepository> { DevelopmentCalcRepositoryImpl(get()) }

        // UseCases
        factory<CalculateDevelopmentUseCase> { CalculateDevelopmentUseCaseImpl(get()) }

        // ViewModels
        viewModel { DevelopmentCalculatorViewModel(get(), get(qualifier = named("development"))) }
    }
