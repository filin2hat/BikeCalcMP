package dev.filinhat.bikecalc.data.service

import dev.filinhat.bikecalc.core.enums.unit.WeightUnit
import dev.filinhat.bikecalc.core.enums.wheel.WheelSize
import dev.filinhat.bikecalc.core.model.PressureCoefficients
import dev.filinhat.bikecalc.data.pressure.service.PressureCalculationServiceImpl
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

/**
 * Тестовый класс для [PressureCalculationServiceImpl].
 */
class PressureCalculationServiceImplTest {
    private val service = PressureCalculationServiceImpl()

    /**
     * Тестирует расчет давления в переднем колесе с использованием килограммов в качестве единицы веса.
     * Убеждается, что рассчитанное давление соответствует ожидаемому значению.
     */
    @Test
    fun `testCalculateWheelPressure_FrontWheel_KG`() {
        // Подготовка
        val riderWeight = 70.0
        val bikeWeight = 10.0
        val wheelSizeInches = WheelSize.Inches29.inchesSize
        val tireWidthMM = 50.0
        val weightUnit = WeightUnit.KG
        val coefficients = PressureCoefficients(0.52, 0.6, 71.0, 65.0) // Из Inches29
        val isFront = true
        val expectedPressure =
            ((riderWeight * coefficients.frontFactor + bikeWeight * coefficients.frontFactor) / (wheelSizeInches * tireWidthMM)) *
                coefficients.frontEmpiricalCoefficient

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

        assertEquals(
            expectedPressure,
            actualPressure,
            0.001,
        ) // Используем дельту для сравнения Double
    }

    /**
     * Тестирует расчет давления в заднем колесе с использованием фунтов в качестве единицы веса.
     * Убеждается, что рассчитанное давление соответствует ожидаемому значению после преобразования веса.
     */
    @Test
    fun `testCalculateWheelPressure_RearWheel_LBS`() {
        val riderWeightLbs = 154.324
        val bikeWeightLbs = 22.0462
        val wheelSizeInches = WheelSize.Inches275.inchesSize // 27.5 дюймов
        val tireWidthMM = 55.0
        val weightUnit = WeightUnit.LBS
        val coefficients = PressureCoefficients(0.5, 0.6, 68.0, 62.5) // Из Inches275
        val isFront = false

        // Конвертируем фунты в кг для расчета ожидаемого значения, так как функция делает это внутри
        val riderWeightKg = riderWeightLbs * 0.453592
        val bikeWeightKg = bikeWeightLbs * 0.453592
        val expectedPressure =
            ((riderWeightKg * coefficients.rearFactor + bikeWeightKg * coefficients.rearFactor) / (wheelSizeInches * tireWidthMM)) *
                coefficients.rearEmpiricalCoefficient

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

        assertEquals(
            expectedPressure,
            actualPressure,
            0.001,
        ) // Используем дельту для сравнения Double
    }

    /**
     * Тестирует, что при нулевом размере покрышки выбрасывается исключение [IllegalArgumentException].
     */
    @Test
    fun `testCalculateWheelPressure_ZeroTireSize_ThrowsIllegalArgumentException`() {
        val riderWeight = 70.0
        val bikeWeight = 10.0
        val wheelSizeInches = WheelSize.Inches29.inchesSize
        val tireWidthMM = 0.0 // Некорректный размер покрышки
        val weightUnit = WeightUnit.KG
        val coefficients = PressureCoefficients(0.52, 0.6, 71.0, 65.0)
        val isFront = true

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

    /**
     * Тестирует, что при отрицательном весе велосипедиста выбрасывается исключение [IllegalArgumentException].
     */
    @Test
    fun `testCalculateWheelPressure_NegativeRiderWeight_ThrowsIllegalArgumentException`() {
        val riderWeight = -70.0 // Некорректный вес
        val bikeWeight = 10.0
        val wheelSizeInches = WheelSize.Inches29.inchesSize
        val tireWidthMM = 50.0
        val weightUnit = WeightUnit.KG
        val coefficients = PressureCoefficients(0.52, 0.6, 71.0, 65.0)
        val isFront = true

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
