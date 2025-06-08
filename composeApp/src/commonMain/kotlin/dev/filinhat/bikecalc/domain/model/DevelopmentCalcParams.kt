package dev.filinhat.bikecalc.domain.model

/**
 * Параметры для расчёта развития метража (метры на оборот педалей).
 *
 * @property rimDiameterMm Диаметр обода колеса в миллиметрах (например, 622 для 700C)
 * @property tireWidthMm Ширина шины в миллиметрах (например, 25)
 * @property frontTeethList Список чисел зубьев передних звёзд (например, [50, 34])
 * @property rearTeethList Список чисел зубьев задних звёзд (например, [12, 13, 15, ...])
 */
data class DevelopmentCalcParams(
    val rimDiameterMm: Double,
    val tireWidthMm: Double,
    val frontTeethList: List<Int>,
    val rearTeethList: List<Int>,
)
