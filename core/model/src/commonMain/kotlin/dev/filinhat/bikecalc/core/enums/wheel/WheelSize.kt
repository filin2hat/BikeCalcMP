package dev.filinhat.bikecalc.core.enums.wheel

import kotlinx.serialization.Serializable

/**
 * Список диаметров колес велосипеда в дюймах.
 *
 * @param nameSize название размера в дюймах и диаметр по стандарту ISO
 * @param inchesSize диаметр в дюймах
 */
@Serializable
enum class WheelSize(
    val nameSize: String,
    val inchesSize: Double,
    val etrtoMm: Int,
) {
    Inches29("29\" (ISO 622 mm)", 29.0, 622),
    Inches28("28\" (ISO 622mm / 700C)", 28.0, 622),
    Inches275("27.5\" (ISO 584 mm / 650B)", 27.5, 584),
    Inches26("26\" (ISO 559 - 590 mm)", 26.0, 559),
    Inches24("24\" (ISO 507 - 540 mm)", 24.0, 507),
    Inches20BMX("20\" (ISO 406 mm)", 20.0, 406),
    Inches20("20\" (ISO 451 mm)", 20.0, 451),
}
