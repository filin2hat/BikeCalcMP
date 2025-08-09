package dev.filinhat.bikecalc.core.database

import androidx.room.RoomDatabase

/**
 * Фабрика базы данных в зависимости от платформы
 */
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class DatabaseFactory {
    fun createDatabase(): RoomDatabase.Builder<PressureResultsDatabase>
}

