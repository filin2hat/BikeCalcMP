package dev.filinhat.bikecalc.core.common.util

import kotlin.math.ceil
import kotlin.math.pow

/**
 * Функция для форматирования числа с заданной точностью
 */
fun formatDoubleToString(
    value: Double,
    decimalPlaces: Int = 1,
): String {
    val factor = 10.0.pow(decimalPlaces)
    val rounded = ceil(value * factor) / factor
    val parts = rounded.toString().split('.')
    val intPart = parts[0]
    val fracPart = parts.getOrNull(1).orEmpty()

    return if (decimalPlaces > 0) {
        val padded = (fracPart + "0".repeat(decimalPlaces)).take(decimalPlaces)
        "$intPart.$padded"
    } else {
        intPart
    }
}

/**
 * Функция для безопасного преобразования строки в Double
 */
fun String.toBikeDouble(): Double = this.trim().replace(',', '.').toDoubleOrNull() ?: 0.0
