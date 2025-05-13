package dev.filinhat.bikecalc.presentation.screen.pressure

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import bikecalcmp.composeapp.generated.resources.Res
import bikecalcmp.composeapp.generated.resources.app_bar_title
import bikecalcmp.composeapp.generated.resources.dialog_text_chapter_four
import bikecalcmp.composeapp.generated.resources.dialog_text_chapter_one
import bikecalcmp.composeapp.generated.resources.dialog_text_chapter_three
import bikecalcmp.composeapp.generated.resources.dialog_text_chapter_two
import bikecalcmp.composeapp.generated.resources.dialog_text_end
import bikecalcmp.composeapp.generated.resources.dialog_title
import bikecalcmp.composeapp.generated.resources.new_calculation
import bikecalcmp.composeapp.generated.resources.previous_results
import compose.icons.FeatherIcons
import compose.icons.feathericons.Info
import dev.filinhat.bikecalc.presentation.ui.kit.common.InfoDialog
import org.jetbrains.compose.resources.stringResource

/**
 * Экран расчета давления велосипеда.
 *
 * @param viewModel [PressureCalculatorViewModel]
 */
@Composable
internal fun PressureCalculatorScreenRoot(viewModel: PressureCalculatorViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    PressureCalculatorScreen(
        uiState = uiState,
        onAction = viewModel::onAction,
        modifier = Modifier,
    )
}

@Composable
private fun PressureCalculatorScreen(
    uiState: PressureCalcState,
    onAction: (PressureCalcAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    var openInfoDialog by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val pagerState = rememberPagerState { 2 }
    val currentOnAction by rememberUpdatedState(onAction)

    LaunchedEffect(uiState.selectedTabIndex) {
        pagerState.animateScrollToPage(uiState.selectedTabIndex)
    }
    LaunchedEffect(pagerState.currentPage) {
        currentOnAction(PressureCalcAction.OnTabSelected(pagerState.currentPage))
    }

    if (openInfoDialog) {
        InfoDialog(
            onCloseDialog = { openInfoDialog = false },
            dialogTitle = stringResource(Res.string.dialog_title),
            dialogText =
                stringResource(Res.string.dialog_text_chapter_one) + "\n\n" +
                    stringResource(Res.string.dialog_text_chapter_two) + "\n" +
                    stringResource(Res.string.dialog_text_chapter_three) + "\n" +
                    stringResource(Res.string.dialog_text_chapter_four) + "\n\n" +
                    stringResource(Res.string.dialog_text_end),
            icon = FeatherIcons.Info,
        )
    }
    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(Res.string.app_bar_title),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
            )

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
                modifier = Modifier.size(48.dp),
            ) {
                Icon(
                    imageVector = FeatherIcons.Info,
                    contentDescription = null,
                )
            }
        }

        TabRow(
            selectedTabIndex = uiState.selectedTabIndex,
            modifier =
                Modifier
                    .padding(vertical = 12.dp)
                    .widthIn(max = 700.dp)
                    .fillMaxWidth(),
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    modifier =
                        Modifier
                            .tabIndicatorOffset(tabPositions[uiState.selectedTabIndex]),
                )
            },
        ) {
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
        }

        Spacer(modifier = Modifier.height(4.dp))

        HorizontalPager(
            state = pagerState,
            modifier =
                Modifier.fillMaxSize(),
        ) { pageIndex ->
            Column(
                modifier = Modifier.fillMaxSize(),
            ) {
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
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(text = "TODO")
                        }
                    }
                }
            }
        }
    }
}
