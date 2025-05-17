package dev.filinhat.bikecalc.domain.mapper

import dev.filinhat.bikecalc.domain.entity.PressureResultEntity
import dev.filinhat.bikecalc.domain.model.SavedPressureCalcResult

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

fun PressureResultEntity.toSavedPressureCalcResult(): SavedPressureCalcResult =
    SavedPressureCalcResult(
        pressureFront = pressureFront,
        pressureRear = pressureRear,
        riderWeight = riderWeight,
        bikeWeight = bikeWeight,
        wheelSize = wheelSize,
        tireSize = tireSize,
    )
