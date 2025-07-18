package dev.filinhat.bikecalc.presentation.features.pressure

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import bikecalcmp.composeapp.generated.resources.Res
import bikecalcmp.composeapp.generated.resources.app_name
import bikecalcmp.composeapp.generated.resources.dialog_text_chapter_four
import bikecalcmp.composeapp.generated.resources.dialog_text_chapter_one
import bikecalcmp.composeapp.generated.resources.dialog_text_chapter_three
import bikecalcmp.composeapp.generated.resources.dialog_text_chapter_two
import bikecalcmp.composeapp.generated.resources.dialog_text_end
import bikecalcmp.composeapp.generated.resources.dialog_title
import bikecalcmp.composeapp.generated.resources.ic_dark_mode
import bikecalcmp.composeapp.generated.resources.ic_light_mode
import bikecalcmp.composeapp.generated.resources.new_calculation
import bikecalcmp.composeapp.generated.resources.previous_results
import compose.icons.LineAwesomeIcons
import compose.icons.lineawesomeicons.InfoCircleSolid
import dev.filinhat.bikecalc.presentation.features.pressure.components.PressureResultContent
import dev.filinhat.bikecalc.presentation.features.pressure.components.PressureScreenContent
import dev.filinhat.bikecalc.presentation.features.pressure.state.PressureCalcAction
import dev.filinhat.bikecalc.presentation.features.pressure.state.PressureCalcState
import dev.filinhat.bikecalc.presentation.features.pressure.viewmodel.PressureCalculatorViewModel
import dev.filinhat.bikecalc.presentation.kit.InfoDialog
import dev.filinhat.bikecalc.presentation.theme.BikeCalcTheme
import dev.filinhat.bikecalc.presentation.theme.LocalThemeIsDark
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Экран расчета давления велосипеда.
 *
 * @param viewModel [PressureCalculatorViewModel]
 */
@Composable
internal fun PressureCalculatorScreenRoot(
    viewModel: PressureCalculatorViewModel,
    keyboardController: SoftwareKeyboardController?,
    focusManager: FocusManager,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    PressureCalculatorScreen(
        uiState = uiState,
        onAction = viewModel::onAction,
        modifier = Modifier,
        keyboardController = keyboardController,
        focusManager = focusManager,
    )
}

@Composable
private fun PressureCalculatorScreen(
    uiState: PressureCalcState,
    onAction: (PressureCalcAction) -> Unit,
    modifier: Modifier = Modifier,
    keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current,
    focusManager: FocusManager? = LocalFocusManager.current,
) {
    val pagerState = rememberPagerState { 2 }
    val currentOnAction by rememberUpdatedState(onAction)

    LaunchedEffect(uiState.selectedTabIndex) {
        pagerState.animateScrollToPage(uiState.selectedTabIndex)
    }
    LaunchedEffect(pagerState.currentPage) {
        currentOnAction(PressureCalcAction.OnTabSelected(pagerState.currentPage))
    }

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 1.dp,
            modifier =
                Modifier
                    .padding(top = 16.dp, bottom = 10.dp)
                    .widthIn(max = 700.dp)
                    .clip(MaterialTheme.shapes.medium),
        ) {
            TabRow(
                selectedTabIndex = uiState.selectedTabIndex,
                Modifier
                    .fillMaxWidth(),
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        modifier =
                            Modifier
                                .tabIndicatorOffset(tabPositions[uiState.selectedTabIndex]),
                    )
                },
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                tabs = {
                    Tab(
                        selected = uiState.selectedTabIndex == 0,
                        onClick = {
                            onAction(PressureCalcAction.OnTabSelected(0))
                        },
                        modifier = Modifier.weight(1f),
                        selectedContentColor = MaterialTheme.colorScheme.primary,
                        unselectedContentColor = MaterialTheme.colorScheme.onSurface,
                    ) {
                        Text(
                            text = stringResource(Res.string.new_calculation),
                            modifier = Modifier.padding(vertical = 12.dp),
                        )
                    }

                    Tab(
                        selected = uiState.selectedTabIndex == 1,
                        onClick = {
                            onAction(PressureCalcAction.OnTabSelected(1))
                        },
                        modifier = Modifier.weight(1f),
                        selectedContentColor = MaterialTheme.colorScheme.primary,
                        unselectedContentColor = MaterialTheme.colorScheme.onSurface,
                    ) {
                        Text(
                            text = stringResource(Res.string.previous_results),
                            modifier = Modifier.padding(vertical = 12.dp),
                        )
                    }
                },
            )
        }

        HorizontalPager(
            pageSpacing = 16.dp,
            state = pagerState,
            modifier =
                Modifier
                    .widthIn(max = 700.dp)
                    .fillMaxWidth(),
        ) { pageIndex ->
            when (pageIndex) {
                0 -> {
                    PressureScreenContent(
                        uiState = uiState,
                        onAction = onAction,
                        keyboardController = keyboardController,
                        focusManager = focusManager,
                    )
                }

                1 -> {
                    PressureResultContent(
                        onAction = onAction,
                        savedResults = uiState.savedCalcResult,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PressureCalculatorScreenPreview() {
    BikeCalcTheme {
        PressureCalculatorScreen(
            uiState = PressureCalcState(),
            onAction = {},
        )
    }
}
