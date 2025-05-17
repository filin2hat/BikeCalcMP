package dev.filinhat.bikecalc.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import dev.filinhat.bikecalc.domain.entity.PressureResultEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO для работы с сохранёнными результатами измерений давления в колёсах.
 *
 * Предоставляет методы для добавления, удаления и получения результатов.
 */
@Dao
interface PressureResultsDao {
    @Upsert
    suspend fun upsert(pressureResult: PressureResultEntity)

    @Query("SELECT * FROM PressureResultsTable")
    fun getAllResults(): Flow<List<PressureResultEntity>>

    @Query("DELETE FROM PressureResultsTable")
    suspend fun deleteAllResults()
}
