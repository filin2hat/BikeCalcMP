package dev.filinhat.bikecalc

import android.app.Application
import dev.filinhat.bikecalc.di.initKoin
import org.koin.android.ext.koin.androidContext

class BikeCalcApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@BikeCalcApplication)
        }
    }
}
