package dev.filinhat.bikecalc.core.common.util

/**
 * Преобразует значение давления из бар в килопаскали (кПа).
 * @return Значение давления в кПа.
 */
fun Double.barToKPa(): Double = this * 100

/**
 * Преобразует значение давления из бар в фунты на квадратный дюйм (psi).
 * @return Значение давления в psi.
 */
fun Double.barToPsi(): Double = this * 14.5038

/**
 * Преобразует значение массы из фунтов (lbs) в килограммы (кг).
 * @return Значение массы в кг.
 */
fun Double.lbsToKg(): Double = this / 2.20462

/**
 * Преобразует значение массы из килограммов (кг) в фунты (lbs).
 * @return Значение массы в фунтах.
 */
fun Double.kgToLbs(): Double = this * 2.20462
