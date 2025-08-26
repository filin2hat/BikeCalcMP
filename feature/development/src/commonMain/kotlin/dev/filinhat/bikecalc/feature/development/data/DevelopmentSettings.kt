package dev.filinhat.bikecalc.feature.development.data

import dev.filinhat.bikecalc.core.enums.wheel.WheelSize
import kotlinx.serialization.Serializable

/**
 * Модель данных для сохранения настроек экрана развития метража.
 * Содержит все параметры, необходимые для расчёта развития метража велосипеда.
 *
 * @property wheelSize Размер колеса велосипеда (по умолчанию 29 дюймов)
 * @property tireWidth Ширина покрышки в миллиметрах в виде строки (по умолчанию "57")
 * @property frontTeethInputs Список строк с количеством зубьев передних звёздочек (по умолчанию ["32"])
 * @property rearTeeth Строка с количеством зубьев задних звёздочек, разделённых запятыми (по умолчанию "10,12,14,16,18,21,24,28,33,39,45,51")
 */
@Serializable
data class DevelopmentSettings(
    val wheelSize: WheelSize = WheelSize.Inches29,
    val tireWidth: String = "57",
    val frontTeethInputs: List<String> = listOf("32"),
    val rearTeeth: String = "10,12,14,16,18,21,24,28,33,39,45,51",
)
