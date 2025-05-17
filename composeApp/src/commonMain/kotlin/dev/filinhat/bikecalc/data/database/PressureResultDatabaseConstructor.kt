package dev.filinhat.bikecalc.data.database

import androidx.room.RoomDatabaseConstructor

/**
 * Конструктор базы данных в зависимости от платформ. Используется для в новых версиях Room
 */
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object PressureResultDatabaseConstructor : RoomDatabaseConstructor<PressureResultsDatabase> {
    override fun initialize(): PressureResultsDatabase
}