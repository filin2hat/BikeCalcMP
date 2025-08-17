package dev.filinhat.bikecalc.core.database

import androidx.room.RoomDatabaseConstructor

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect object PressureResultDatabaseConstructor : RoomDatabaseConstructor<PressureResultsDatabase> {
    override fun initialize(): PressureResultsDatabase
}
