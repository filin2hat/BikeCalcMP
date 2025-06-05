package dev.filinhat.bikecalc.data.repository

import dev.filinhat.bikecalc.data.database.PressureResultsDao
import dev.filinhat.bikecalc.domain.entity.PressureResultEntity
import dev.filinhat.bikecalc.domain.enums.tube.TubeType
import dev.filinhat.bikecalc.domain.mapper.toSavedPressureCalcResult
import dev.filinhat.bikecalc.domain.model.PressureCalcParams
import dev.filinhat.bikecalc.domain.model.PressureCalcResult
import dev.filinhat.bikecalc.domain.model.SavedPressureCalcResult
import dev.filinhat.bikecalc.domain.repository.PressureCalcRepository
import dev.filinhat.bikecalc.domain.service.PressureCalculationService
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
            saveCalcResult(
                params = params,
                frontPressureTubes = result.tubesFront,
                rearPressureTubes = result.tubesRear,
                frontPressureTubeless = result.tubelessFront,
                rearPressureTubeless = result.tubelessRear,
            )
            emit(result)
        }.flowOn(Dispatchers.IO)

    override suspend fun saveCalcResult(
        params: PressureCalcParams,
        frontPressureTubes: Double,
        rearPressureTubes: Double,
        frontPressureTubeless: Double,
        rearPressureTubeless: Double,
    ) {
        pressureResultDao.insertWithLimit(
            PressureResultEntity(
                riderWeight = params.riderWeight,
                bikeWeight = params.bikeWeight,
                wheelSize = params.wheelSize.inchesSize.toString(),
                tireSize = params.tireSize.tireWidthInInches.toString(),
                pressureFront = if (params.selectedTubeType == TubeType.TUBES) frontPressureTubes else frontPressureTubeless,
                pressureRear = if (params.selectedTubeType == TubeType.TUBES) rearPressureTubes else rearPressureTubeless,
                id = null,
            ),
        )
    }

    override suspend fun deleteAllResults() {
        pressureResultDao.deleteAllResults()
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
