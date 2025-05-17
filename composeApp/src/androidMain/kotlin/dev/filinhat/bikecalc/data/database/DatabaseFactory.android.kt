package dev.filinhat.bikecalc.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

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
