package dev.filinhat.bikecalc.data.service

import dev.filinhat.bikecalc.domain.model.*
import dev.filinhat.bikecalc.domain.service.DevelopmentCalculationService
import kotlin.math.PI

/**
 * Реализация сервиса для расчёта развития метража (метры на оборот педалей).
 */
class DevelopmentCalculationServiceImpl : DevelopmentCalculationService {
    /**
     * Выполняет расчёт развития метража для всех комбинаций передних и задних звёзд.
     *
     * @param params параметры для расчёта развития метража
     * @return список результатов для каждой передачи (комбинации звёзд)
     */
    override fun calculateDevelopment(params: DevelopmentCalcParams): List<DevelopmentCalcResult> {
        val effectiveDiameterMm = params.rimDiameterMm + 2 * params.tireWidthMm
        val circumferenceM = effectiveDiameterMm * PI / 1000.0

        return params.frontTeethList.flatMap { front ->
            params.rearTeethList.map { rear ->
                val ratio = front.toDouble() / rear
                val development = circumferenceM * ratio
                DevelopmentCalcResult(front, rear, development)
            }
        }
    }
}
