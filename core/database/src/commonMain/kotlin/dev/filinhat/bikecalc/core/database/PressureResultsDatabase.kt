package dev.filinhat.bikecalc.core.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import dev.filinhat.bikecalc.core.database.dao.PressureResultsDao
import dev.filinhat.bikecalc.core.database.entity.PressureResultEntity

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
