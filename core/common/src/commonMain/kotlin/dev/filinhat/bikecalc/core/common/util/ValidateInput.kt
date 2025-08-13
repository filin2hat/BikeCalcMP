package dev.filinhat.bikecalc.core.common.util

/**
 * Общая функция валидации числового ввода.
 * @param input Входная строка
 * @param minValue Минимальное допустимое значение
 * @param maxValue Максимальное допустимое значение
 * @return true если валидация успешна
 */
fun validateNumberInput(
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


/**
 * Проверка допустимости промежуточного ввода для одного положительного целого числа.
 * Разрешены только цифры 0-9 (включая пустую строку как промежуточное состояние).
 */
fun isAllowedSinglePositiveIntInput(input: String): Boolean {
    return input.matches(Regex("^[0-9]*$"))
}

/**
 * Проверка валидности значения одного положительного целого числа в диапазоне [minValue, maxValue].
 * Пустая строка невалидна как итоговое значение.
 */
fun validateSinglePositiveInt(input: String, minValue: Int = 1, maxValue: Int = 1000): Boolean {
    if (input.isEmpty()) return false
    if (!isAllowedSinglePositiveIntInput(input)) return false
    val value = input.toIntOrNull() ?: return false
    return value in minValue..maxValue
}

/**
 * Проверка допустимости промежуточного ввода для списка положительных целых чисел через запятую.
 * Разрешены только цифры и запятые (включая пустую строку как промежуточное состояние).
 */
fun isAllowedPositiveIntCsvInput(input: String): Boolean {
    return input.matches(Regex("^[0-9,]*$"))
}

/**
 * Проверка валидности списка положительных целых чисел через запятую в диапазоне [minValue, maxValue].
 * Требования:
 * - не начинается и не заканчивается запятой
 * - нет последовательностей ",,"
 * - каждый элемент парсится в Int и попадает в диапазон
 */
fun validatePositiveIntCsv(input: String, minValue: Int = 1, maxValue: Int = 1000): Boolean {
    if (input.isEmpty()) return false
    if (!isAllowedPositiveIntCsvInput(input)) return false
    if (input.first() == ',' || input.last() == ',') return false
    if (",," in input) return false

    val parts = input.split(',')
    if (parts.isEmpty()) return false
    return parts.all { part ->
        val value = part.toIntOrNull() ?: return@all false
        value in minValue..maxValue
    }
}