package dev.filinhat.bikecalc.core.database.mapper

import dev.filinhat.bikecalc.core.database.entity.PressureResultEntity
import dev.filinhat.bikecalc.core.model.SavedPressureCalcResult

/**
 * Маппер преобразования [SavedPressureCalcResult] в [PressureResultEntity]
 */
fun SavedPressureCalcResult.toPressureResultEntity(): PressureResultEntity =
    PressureResultEntity(
        id = null,
        pressureFront = pressureFront,
        pressureRear = pressureRear,
        riderWeight = riderWeight,
        bikeWeight = bikeWeight,
        wheelSize = wheelSize,
        tireSize = tireSize,
    )

/**
 * Маппер преобразования [PressureResultEntity] в [SavedPressureCalcResult]
 */
fun PressureResultEntity.toSavedPressureCalcResult(): SavedPressureCalcResult =
    SavedPressureCalcResult(
        id = id ?: 0,
        pressureFront = pressureFront,
        pressureRear = pressureRear,
        riderWeight = riderWeight,
        bikeWeight = bikeWeight,
        wheelSize = wheelSize,
        tireSize = tireSize,
    )

