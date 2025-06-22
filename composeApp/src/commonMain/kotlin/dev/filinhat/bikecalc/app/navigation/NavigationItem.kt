package dev.filinhat.bikecalc.app.navigation

internal data class NavigationItem(
    val route: BikeCalcRoute,
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
)
