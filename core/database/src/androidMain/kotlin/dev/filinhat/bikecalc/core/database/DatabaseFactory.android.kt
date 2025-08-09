package dev.filinhat.bikecalc.core.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class DatabaseFactory(
    private val context: Context,
) {
    actual fun createDatabase(): RoomDatabase.Builder<PressureResultsDatabase> {
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath(PressureResultsDatabase.DATABASE_NAME)

        return Room.databaseBuilder(
            context = appContext,
            name = dbFile.absolutePath,
        )
    }
}

