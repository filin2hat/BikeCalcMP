package dev.filinhat.bikecalc.domain.model

/**
 * Результат расчёта развития метража для одной передачи.
 *
 * @property frontTeeth Число зубьев на передней звезде
 * @property rearTeeth Число зубьев на задней звезде
 * @property developmentMeters Пройденное расстояние (в метрах) за один оборот педалей
 */
data class DevelopmentCalcResult(
    val frontTeeth: Int,
    val rearTeeth: Int,
    val developmentMeters: Double,
)
