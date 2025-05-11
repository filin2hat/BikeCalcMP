package dev.filinhat.bikecalc.presentation.util

/**
 * Валидация ввода значения веса водителя.
 */
fun validateUserWeight(input: String): Boolean = input.toDoubleOrNull()?.let { it in 1.0..200.0 } == true

/**
 * Валидация ввода значения веса велосипеда.
 */
fun validateBikeWeight(input: String): Boolean = input.toDoubleOrNull()?.let { it in 1.0..100.0 } == true
