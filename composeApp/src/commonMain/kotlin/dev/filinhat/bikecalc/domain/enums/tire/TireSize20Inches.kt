package dev.filinhat.bikecalc.domain.enums.tire

enum class TireSize20Inches(
    override val nameSize: String,
    override val tireWidthInInches: Double,
    override val tireWidthInMillimeters: Double,
) : TireSize {
    Size20x1_1_8("20 x 1 1/8 (28 - 451)", 1.125, 28.0),
    Size20x1_3_8("20 x 1 3/8 (37 - 451)", 1.375, 37.0),
    Size20x1_1_4("20 x 1 1/4 (32 - 451)", 1.25, 32.0),
    Size20x1_3_4("20 x 1 3/4 (47 - 451)", 1.75, 47.0),
    Size20x135("20 x 1.35 (35 - 451)", 1.35, 35.0),
    Size20x140("20 x 1.40 (37 - 451)", 1.40, 37.0),
    Size20x150x("20 x 1.50 (40 - 451)", 1.50, 40.0),
    Size20x175x("20 x 1.75 (47 - 451)", 1.75, 47.0),
}
