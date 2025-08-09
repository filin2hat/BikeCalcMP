package dev.filinhat.bikecalc.data.pressure.repository

import dev.filinhat.bikecalc.core.database.dao.PressureResultsDao
import dev.filinhat.bikecalc.core.database.entity.PressureResultEntity
import dev.filinhat.bikecalc.core.database.mapper.toSavedPressureCalcResult
import dev.filinhat.bikecalc.core.model.PressureCalcParams
import dev.filinhat.bikecalc.core.model.PressureCalcResult
import dev.filinhat.bikecalc.core.model.SavedPressureCalcResult
import dev.filinhat.bikecalc.domain.pressure.repository.PressureCalcRepository
import dev.filinhat.bikecalc.domain.pressure.service.PressureCalculationService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class PressureCalcRepositoryImpl(
    private val pressureResultDao: PressureResultsDao,
    private val pressureCalculationService: PressureCalculationService,
) : PressureCalcRepository {
    override fun calcPressure(params: PressureCalcParams): Flow<PressureCalcResult> =
        flow {
            val result = pressureCalculationService.calculatePressure(params)
            emit(result)
        }.flowOn(Dispatchers.IO)

    override suspend fun saveCalcResult(
        params: PressureCalcParams,
        frontPressure: Double,
        rearPressure: Double,
    ) {
        pressureResultDao.insertWithLimit(
            PressureResultEntity(
                riderWeight = params.riderWeight,
                bikeWeight = params.bikeWeight,
                wheelSize = params.wheelSize.inchesSize.toString(),
                tireSize = params.tireSize.tireWidthInInches.toString(),
                pressureFront = frontPressure,
                pressureRear = rearPressure,
                id = null,
            ),
        )
    }

    override suspend fun deleteAllResults() {
        pressureResultDao.deleteAllResults()
    }

    override suspend fun deleteResult(id: Long) {
        pressureResultDao.deleteResult(id)
    }

    override fun getAllResults(): Flow<List<SavedPressureCalcResult>> =
        pressureResultDao
            .getAllResults()
            .map { entities ->
                entities.map { entity ->
                    entity.toSavedPressureCalcResult()
                }
            }.flowOn(Dispatchers.Default)
}

