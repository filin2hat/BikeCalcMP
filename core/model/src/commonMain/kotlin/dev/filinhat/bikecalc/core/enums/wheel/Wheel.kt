package dev.filinhat.bikecalc.core.enums.wheel

import kotlinx.serialization.Serializable

/**
 * Список колес велосипеда.
 */
@Serializable
enum class Wheel {
    Front,
    Rear,
}
