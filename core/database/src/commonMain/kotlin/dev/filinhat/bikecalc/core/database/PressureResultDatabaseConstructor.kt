package dev.filinhat.bikecalc.core.database

import androidx.room.RoomDatabaseConstructor

/**
 * Конструктор базы данных в зависимости от платформ. Используется для в новых версиях Room
 */
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING", "KotlinNoActualForExpected")
expect object PressureResultDatabaseConstructor : RoomDatabaseConstructor<PressureResultsDatabase> {
    override fun initialize(): PressureResultsDatabase
}

