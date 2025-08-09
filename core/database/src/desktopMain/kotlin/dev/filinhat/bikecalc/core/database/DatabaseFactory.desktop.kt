package dev.filinhat.bikecalc.core.database

import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class DatabaseFactory {
    actual fun createDatabase(): RoomDatabase.Builder<PressureResultsDatabase> {
        val dbFile =
            File(System.getProperty("java.io.tmpdir"), PressureResultsDatabase.DATABASE_NAME)
        return Room.databaseBuilder<PressureResultsDatabase>(
            name = dbFile.absolutePath,
        )
    }
}

