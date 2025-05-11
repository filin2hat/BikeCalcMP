package dev.filinhat.bikecalc.domain.enums.tire

enum class TireSize20InchesBMX(
    override val nameSize: String,
    override val tireWidthInInches: Double,
    override val tireWidthInMillimeters: Double,
) : TireSize {
    // ISO 406 мм (BMX, детские велосипеды)
    Size20x150("20 x 1.50 (40 - 406)", 1.50, 40.0),
    Size20x160("20 x 1.60 (42 - 406)", 1.60, 42.0),
    Size20x175("20 x 1.75 (47 - 406)", 1.75, 47.0),
    Size20x190("20 x 1.90 (50 - 406)", 1.90, 50.0),
    Size20x195("20 x 1.95 (52 - 406)", 1.95, 52.0),
    Size20x200("20 x 2.00 (54 - 406)", 2.00, 54.0),
    Size20x210("20 x 2.10 (57 - 406)", 2.10, 57.0),
    Size20x225("20 x 2.25 (60 - 406)", 2.25, 60.0),
    Size20x235("20 x 2.35 (62 - 406)", 2.35, 62.0),
    Size20x250("20 x 2.50 (64 - 406)", 2.50, 64.0),
}
