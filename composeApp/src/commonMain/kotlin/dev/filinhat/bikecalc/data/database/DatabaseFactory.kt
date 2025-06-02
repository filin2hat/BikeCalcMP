package dev.filinhat.bikecalc.data.database

import androidx.room.RoomDatabase

/**
 * Фабрика базы данных в зависимости от платформы
 */
expect class DatabaseFactory {
    fun createDatabase(): RoomDatabase.Builder<PressureResultsDatabase>
}
