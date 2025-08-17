package dev.filinhat.bikecalc.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import dev.filinhat.bikecalc.core.database.entity.PressureResultEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO для работы с сохранёнными результатами измерений давления в колёсах.
 *
 * Предоставляет методы для добавления, удаления и получения результатов.
 */
@Dao
abstract class PressureResultsDao {
    @Upsert
    abstract suspend fun upsert(pressureResult: PressureResultEntity)

    @Query("SELECT * FROM PressureResultsTable ORDER BY id DESC")
    abstract fun getAllResults(): Flow<List<PressureResultEntity>>

    @Query("DELETE FROM PressureResultsTable")
    abstract suspend fun deleteAllResults()

    @Query("DELETE FROM PressureResultsTable WHERE id = :id")
    abstract suspend fun deleteResult(id: Long)

    @Query("SELECT COUNT(*) FROM PressureResultsTable")
    abstract suspend fun count(): Int

    @Query("DELETE FROM PressureResultsTable WHERE id = (SELECT id FROM PressureResultsTable ORDER BY id ASC LIMIT 1)")
    abstract suspend fun deleteOldest()

    // Получаем две последние записи вместо одной
    @Query("SELECT * FROM PressureResultsTable ORDER BY id DESC LIMIT 2")
    abstract suspend fun getLatestTwoResults(): List<PressureResultEntity>

    @Transaction
    open suspend fun insertWithLimit(entity: PressureResultEntity) {
        // Если это обновление существующей записи
        if (entity.id != null) {
            upsert(entity)
            return
        }

        // Проверяем дубликаты среди последних двух записей
        val latestTwo = getLatestTwoResults()
        val isDuplicate =
            latestTwo.any { existing ->
                entity.riderWeight == existing.riderWeight &&
                    entity.bikeWeight == existing.bikeWeight &&
                    entity.wheelSize == existing.wheelSize &&
                    entity.tireSize == existing.tireSize &&
                    entity.pressureRear == existing.pressureRear
            }

        // Пропускаем сохранение если найден дубликат
        if (isDuplicate) {
            return
        }

        // Проверяем и поддерживаем лимит записей
        if (count() >= 10) {
            deleteOldest()
        }
        upsert(entity)
    }
}
