package dev.filinhat.bikecalc.domain.service

import dev.filinhat.bikecalc.domain.model.PressureCalcParams
import dev.filinhat.bikecalc.domain.model.PressureCalcResult

/**
 * Сервис для расчета давления в колесах велосипеда
 */
interface PressureCalculationService {
    /**
     * Рассчитывает давление в колесах велосипеда
     */
    fun calculatePressure(params: PressureCalcParams): PressureCalcResult
}
