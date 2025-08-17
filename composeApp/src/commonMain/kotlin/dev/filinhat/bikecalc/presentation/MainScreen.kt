package dev.filinhat.bikecalc.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import bikecalcmp.composeapp.generated.resources.Res
import bikecalcmp.composeapp.generated.resources.app_name
import bikecalcmp.composeapp.generated.resources.dialog_text_chapter_four
import bikecalcmp.composeapp.generated.resources.dialog_text_chapter_one
import bikecalcmp.composeapp.generated.resources.dialog_text_chapter_three
import bikecalcmp.composeapp.generated.resources.dialog_text_chapter_two
import bikecalcmp.composeapp.generated.resources.dialog_text_end
import bikecalcmp.composeapp.generated.resources.dialog_title
import bikecalcmp.composeapp.generated.resources.i_understand_this
import bikecalcmp.composeapp.generated.resources.ic_dark_mode
import bikecalcmp.composeapp.generated.resources.ic_light_mode
import bikecalcmp.composeapp.generated.resources.nav_development_calculator
import bikecalcmp.composeapp.generated.resources.nav_pressure_calculator
import compose.icons.LineAwesomeIcons
import compose.icons.lineawesomeicons.CalculatorSolid
import compose.icons.lineawesomeicons.ChartLineSolid
import compose.icons.lineawesomeicons.InfoCircleSolid
import dev.filinhat.bikecalc.app.navigation.BikeCalcNavigation
import dev.filinhat.bikecalc.app.navigation.BikeCalcRoute
import dev.filinhat.bikecalc.app.navigation.NavigationItem
import dev.filinhat.bikecalc.designsystem.component.InfoDialog
import dev.filinhat.bikecalc.designsystem.theme.BikeCalcTheme
import dev.filinhat.bikecalc.designsystem.theme.LocalThemeIsDark
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val startDestination = BikeCalcRoute.PressureCalculator
    var openInfoDialog by remember { mutableStateOf(false) }
    var isDark by LocalThemeIsDark.current
    val iconTheme =
        remember(isDark) {
            if (isDark) {
                Res.drawable.ic_light_mode
            } else {
                Res.drawable.ic_dark_mode
            }
        }

    if (openInfoDialog) {
        InfoDialog(
            onCloseDialog = { openInfoDialog = false },
            dialogTitle = stringResource(Res.string.dialog_title),
            dialogText =
                stringResource(Res.string.dialog_text_chapter_one) + "\n\n" +
                    stringResource(
                        Res.string.dialog_text_chapter_two,
                    ) + "\n" + stringResource(Res.string.dialog_text_chapter_three) + "\n" +
                    stringResource(
                        Res.string.dialog_text_chapter_four,
                    ) + "\n\n" + stringResource(Res.string.dialog_text_end),
            icon = LineAwesomeIcons.InfoCircleSolid,
            buttonText = stringResource(Res.string.i_understand_this),
        )
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                windowInsets = NavigationBarDefaults.windowInsets,
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
                tonalElevation = 8.dp,
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                val screens =
                    listOf(
                        NavigationItem(
                            route = BikeCalcRoute.PressureCalculator,
                            title = stringResource(Res.string.nav_pressure_calculator),
                            icon = LineAwesomeIcons.CalculatorSolid,
                        ),
                        NavigationItem(
                            route = BikeCalcRoute.DevelopmentCalculator,
                            title = stringResource(Res.string.nav_development_calculator),
                            icon = LineAwesomeIcons.ChartLineSolid,
                        ),
                    )

                screens.forEach { screen ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = screen.icon,
                                contentDescription = screen.title,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(24.dp),
                            )
                        },
                        label = { Text(screen.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route.toString() } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                    )
                }
            }
        },
        content = { contentPadding ->
            Column(
                modifier =
                    Modifier.fillMaxSize().padding(
                        top = contentPadding.calculateTopPadding(),
                        bottom = contentPadding.calculateBottomPadding(),
                    ),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(Res.string.app_name),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary,
                    )
                    Spacer(modifier = Modifier.weight(1f))

                    IconButton(
                        onClick = {
                            isDark = !isDark
                        },
                        colors =
                            IconButtonDefaults.iconButtonColors(
                                contentColor = MaterialTheme.colorScheme.primary,
                            ),
                        modifier = Modifier.size(28.dp),
                    ) {
                        Icon(vectorResource(iconTheme), contentDescription = null)
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    IconButton(
                        onClick = {
                            keyboardController?.hide()
                            focusManager.clearFocus()
                            openInfoDialog = true
                        },
                        colors =
                            IconButtonDefaults.iconButtonColors(
                                contentColor = MaterialTheme.colorScheme.primary,
                            ),
                        modifier = Modifier.size(28.dp),
                    ) {
                        Icon(
                            imageVector = LineAwesomeIcons.InfoCircleSolid,
                            contentDescription = null,
                        )
                    }
                }
                BikeCalcNavigation(navController, startDestination)
            }
        },
    )
}

@Preview
@Composable
private fun MainScreenPreview() {
    BikeCalcTheme {
        MainScreen()
    }
}
