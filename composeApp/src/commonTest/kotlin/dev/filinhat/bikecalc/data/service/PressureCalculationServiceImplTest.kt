package dev.filinhat.bikecalc.data.service

import dev.filinhat.bikecalc.domain.enums.tube.TubeType
import dev.filinhat.bikecalc.domain.enums.unit.WeightUnit
import dev.filinhat.bikecalc.domain.enums.wheel.WheelSize
import dev.filinhat.bikecalc.domain.model.PressureCoefficients
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class PressureCalculationServiceImplTest {
    private val service = PressureCalculationServiceImpl()

    @Test
    fun `calculateWheelPressure should return correct pressure for front wheel in KG`() {
        // Подготовка
        val riderWeight = 70.0 // кг
        val bikeWeight = 10.0 // кг
        val wheelSizeInches = WheelSize.Inches29.inchesSize // 29.0 дюймов
        val tireWidthMM = 50.0 // мм
        val weightUnit = WeightUnit.KG
        val coefficients = PressureCoefficients(0.52, 0.6, 71.0, 65.0) // Из Inches29
        val isFront = true
        val expectedPressure =
            ((riderWeight * coefficients.frontFactor + bikeWeight * coefficients.frontFactor) / (wheelSizeInches * tireWidthMM)) *
                coefficients.frontEmpiricalCoefficient

        // Действие
        val actualPressure =
            service.calculateWheelPressure(
                riderWeight = riderWeight,
                bikeWeight = bikeWeight,
                wheelSize = wheelSizeInches,
                tireSize = tireWidthMM,
                weightUnit = weightUnit,
                coefficients = coefficients,
                isFront = isFront,
            )

        // Проверка
        assertEquals(expectedPressure, actualPressure, 0.001) // Используем дельту для сравнения Double
    }

    @Test
    fun `calculateWheelPressure should return correct pressure for rear wheel in LBS`() {
        // Подготовка
        val riderWeightLbs = 154.324 // 70 кг
        val bikeWeightLbs = 22.0462 // 10 кг
        val wheelSizeInches = WheelSize.Inches275.inchesSize // 27.5 дюймов
        val tireWidthMM = 55.0 // мм
        val weightUnit = WeightUnit.LBS
        val coefficients = PressureCoefficients(0.5, 0.6, 68.0, 62.5) // Из Inches275
        val isFront = false

        // Конвертируем фунты в кг для расчета ожидаемого значения, так как функция делает это внутри
        val riderWeightKg = riderWeightLbs * 0.453592
        val bikeWeightKg = bikeWeightLbs * 0.453592
        val expectedPressure =
            ((riderWeightKg * coefficients.rearFactor + bikeWeightKg * coefficients.rearFactor) / (wheelSizeInches * tireWidthMM)) *
                coefficients.rearEmpiricalCoefficient

        // Действие
        val actualPressure =
            service.calculateWheelPressure(
                riderWeight = riderWeightLbs,
                bikeWeight = bikeWeightLbs,
                wheelSize = wheelSizeInches,
                tireSize = tireWidthMM,
                weightUnit = weightUnit,
                coefficients = coefficients,
                isFront = isFront,
            )

        // Проверка
        assertEquals(expectedPressure, actualPressure, 0.001) // Используем дельту для сравнения Double
    }

    @Test
    fun `calculateWheelPressure должен выбрасывать IllegalArgumentException для нулевого размера покрышки`() {
        // Подготовка
        val riderWeight = 70.0
        val bikeWeight = 10.0
        val wheelSizeInches = WheelSize.Inches29.inchesSize
        val tireWidthMM = 0.0 // Некорректный размер покрышки
        val weightUnit = WeightUnit.KG
        val coefficients = PressureCoefficients(0.52, 0.6, 71.0, 65.0)
        val isFront = true

        // Действие и Проверка
        assertFailsWith<IllegalArgumentException> {
            service.calculateWheelPressure(
                riderWeight = riderWeight,
                bikeWeight = bikeWeight,
                wheelSize = wheelSizeInches,
                tireSize = tireWidthMM,
                weightUnit = weightUnit,
                coefficients = coefficients,
                isFront = isFront,
            )
        }
    }

    @Test
    fun `calculateWheelPressure должен выбрасывать IllegalArgumentException для отрицательного веса велосипедиста`() {
        // Подготовка
        val riderWeight = -70.0 // Некорректный вес
        val bikeWeight = 10.0
        val wheelSizeInches = WheelSize.Inches29.inchesSize
        val tireWidthMM = 50.0
        val weightUnit = WeightUnit.KG
        val coefficients = PressureCoefficients(0.52, 0.6, 71.0, 65.0)
        val isFront = true

        // Действие и Проверка
        assertFailsWith<IllegalArgumentException> {
            service.calculateWheelPressure(
                riderWeight = riderWeight,
                bikeWeight = bikeWeight,
                wheelSize = wheelSizeInches,
                tireSize = tireWidthMM,
                weightUnit = weightUnit,
                coefficients = coefficients,
                isFront = isFront,
            )
        }
    }
}
