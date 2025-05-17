package dev.filinhat.bikecalc.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Таблица сохранения результатов вычислений давления колес.
 */
@Entity(tableName = "PressureResultsTable")
data class PressureResultEntity(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    val pressureFront: Double,
    val pressureRear: Double,
    val riderWeight: Double,
    val bikeWeight: Double,
    val wheelSize: String,
    val tireSize: String,
)