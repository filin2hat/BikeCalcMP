package dev.filinhat.bikecalc.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * База данных результатов расчета давлений
 */
@Database(
    entities = [PressureResultEntity::class],
    version = 1,
)
@ConstructedBy(PressureResultDatabaseConstructor::class)
abstract class PressureResultsDatabase : RoomDatabase() {
    abstract val dao: PressureResultsDao

    companion object {
        const val DATABASE_NAME = "pressure_results.db"
    }
}
