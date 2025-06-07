package dev.filinhat.bikecalc.data.database

import androidx.room.RoomDatabaseConstructor

/**
 * Конструктор базы данных в зависимости от платформ. Используется для в новых версиях Room
 */
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING", "KotlinNoActualForExpect")
expect object PressureResultDatabaseConstructor : RoomDatabaseConstructor<PressureResultsDatabase> {
    override fun initialize(): PressureResultsDatabase
}
