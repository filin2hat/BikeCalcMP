package dev.filinhat.bikecalc.feature.pressure.di

import dev.filinhat.bikecalc.core.database.DatabaseFactory
import dev.filinhat.bikecalc.core.database.PressureResultsDatabase
import dev.filinhat.bikecalc.core.settings.SettingsStore
import dev.filinhat.bikecalc.feature.pressure.data.PressureSettings
import dev.filinhat.bikecalc.feature.pressure.data.createPressureSettingsStore
import dev.filinhat.bikecalc.feature.pressure.data.repository.PressureCalcRepositoryImpl
import dev.filinhat.bikecalc.feature.pressure.data.service.PressureCalculationServiceImpl
import dev.filinhat.bikecalc.feature.pressure.domain.repository.PressureCalcRepository
import dev.filinhat.bikecalc.feature.pressure.domain.service.PressureCalculationService
import dev.filinhat.bikecalc.feature.pressure.domain.usecase.CalculatePressureUseCase
import dev.filinhat.bikecalc.feature.pressure.domain.usecase.CalculatePressureUseCaseImpl
import dev.filinhat.bikecalc.feature.pressure.domain.usecase.DeleteAllResultsUseCase
import dev.filinhat.bikecalc.feature.pressure.domain.usecase.DeleteAllResultsUseCaseImpl
import dev.filinhat.bikecalc.feature.pressure.domain.usecase.DeleteResultUseCase
import dev.filinhat.bikecalc.feature.pressure.domain.usecase.DeleteResultUseCaseImpl
import dev.filinhat.bikecalc.feature.pressure.domain.usecase.GetSavedResultsUseCase
import dev.filinhat.bikecalc.feature.pressure.domain.usecase.GetSavedResultsUseCaseImpl
import dev.filinhat.bikecalc.feature.pressure.presentation.viewmodel.PressureCalculatorViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Koin модуль для фичи pressure с универсальным механизмом настроек.
 * Предоставляет зависимости для экрана расчета давления велосипеда.
 */
val featurePressureModule =
    module {
        // Database
        single {
            get<DatabaseFactory>().createDatabase().build()
        }
        single { get<PressureResultsDatabase>().dao }

        single<SettingsStore<PressureSettings>>(qualifier = named("pressure")) {
            createPressureSettingsStore()
        }

        single<PressureCalculationService> { PressureCalculationServiceImpl() }

        single<PressureCalcRepository> {
            PressureCalcRepositoryImpl(
                pressureResultDao = get(),
                pressureCalculationService = get(),
            )
        }

        single<CalculatePressureUseCase> { CalculatePressureUseCaseImpl(repository = get()) }
        single<GetSavedResultsUseCase> { GetSavedResultsUseCaseImpl(repository = get()) }
        single<DeleteResultUseCase> { DeleteResultUseCaseImpl(repository = get()) }
        single<DeleteAllResultsUseCase> { DeleteAllResultsUseCaseImpl(repository = get()) }

        // ViewModels
        viewModel {
            PressureCalculatorViewModel(
                calculatePressureUseCase = get(),
                getSavedResultsUseCase = get(),
                deleteResultUseCase = get(),
                deleteAllResultsUseCase = get(),
                settingsStore = get(qualifier = named("pressure")),
                repository = get(),
            )
        }
    }
