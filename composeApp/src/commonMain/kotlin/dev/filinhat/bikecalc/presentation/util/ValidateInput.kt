package dev.filinhat.bikecalc.presentation.util

/**
 * Общая функция валидации числового ввода.
 * @param input Входная строка
 * @param minValue Минимальное допустимое значение
 * @param maxValue Максимальное допустимое значение
 * @return true если валидация успешна
 */
private fun validateNumberInput(
    input: String,
    minValue: Double,
    maxValue: Double,
): Boolean {
    // Нормализуем строку: удаляем пробелы и заменяем запятые на точки
    val normalized = input.trim().replace(',', '.')

    // Проверяем общий формат числа
    val regex = Regex("^\\d+(?:\\.\\d{0,2})?$")
    if (!normalized.matches(regex)) return false

    // Преобразуем в число
    val value = normalized.toDoubleOrNull() ?: return false

    // Проверяем диапазон
    return value in minValue..maxValue
}

/**
 * Валидация ввода значения веса водителя.
 */
fun validateUserWeight(input: String): Boolean = validateNumberInput(input, minValue = 1.0, maxValue = 200.0)

/**
 * Валидация ввода значения веса велосипеда.
 */
fun validateBikeWeight(input: String): Boolean = validateNumberInput(input, minValue = 1.0, maxValue = 100.0)
