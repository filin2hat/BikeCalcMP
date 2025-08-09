package dev.filinhat.bikecalc.domain.pressure.service

import dev.filinhat.bikecalc.core.model.PressureCalcParams
import dev.filinhat.bikecalc.core.model.PressureCalcResult

/**
 * Сервис для расчета давления в колесах велосипеда
 */
interface PressureCalculationService {
    /**
     * Рассчитывает давление в колесах велосипеда
     *
     * @param params параметры для расчета
     * @return результат расчета давления
     */
    fun calculatePressure(params: PressureCalcParams): PressureCalcResult
}

