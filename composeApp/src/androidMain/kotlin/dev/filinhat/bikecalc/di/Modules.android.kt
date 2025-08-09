package dev.filinhat.bikecalc.di

import dev.filinhat.bikecalc.core.database.DatabaseFactory
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() =
        module {
            single { DatabaseFactory(androidApplication()) }
        }
