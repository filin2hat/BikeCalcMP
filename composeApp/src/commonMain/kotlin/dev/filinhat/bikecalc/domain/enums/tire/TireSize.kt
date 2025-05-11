package dev.filinhat.bikecalc.domain.enums.tire

/**
 * Общий интерфейс для размеров колес велосипеда.
 *
 * @property nameSize название размера
 * @property tireWidthInInches размер в дюймах
 * @property tireWidthInMillimeters размер в миллиметрах
 */
interface TireSize {
    val nameSize: String
    val tireWidthInInches: Double
    val tireWidthInMillimeters: Double
}
