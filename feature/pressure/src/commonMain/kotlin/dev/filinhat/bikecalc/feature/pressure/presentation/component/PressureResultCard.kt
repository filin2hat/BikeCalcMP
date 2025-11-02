package dev.filinhat.bikecalc.feature.pressure.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.dp
import bikecalcmp.feature.pressure.generated.resources.Res
import bikecalcmp.feature.pressure.generated.resources.bar
import bikecalcmp.feature.pressure.generated.resources.front_wheel
import bikecalcmp.feature.pressure.generated.resources.rear_wheel
import compose.icons.LineAwesomeIcons
import compose.icons.lineawesomeicons.ArrowsAltHSolid
import compose.icons.lineawesomeicons.BicycleSolid
import compose.icons.lineawesomeicons.CircleNotchSolid
import compose.icons.lineawesomeicons.UserSolid
import dev.filinhat.bikecalc.core.common.util.formatDoubleToString
import dev.filinhat.bikecalc.core.enums.tire.TireSize29Inches
import dev.filinhat.bikecalc.core.enums.wheel.WheelSize
import dev.filinhat.bikecalc.core.model.pressure.SavedPressureCalcResult
import dev.filinhat.bikecalc.designsystem.theme.BikeCalcTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Карточка для отображения сохраненного результата расчета давления.
 * Показывает краткую информацию о расчете: давление переднего и заднего колеса,
 * вес велосипедиста и велосипеда, размер колеса и ширина покрышки.
 * Поддерживает короткое нажатие для просмотра деталей и длинное для удаления.
 *
 * @param result Сохраненный результат расчета давления для отображения
 * @param onClick Callback для обработки короткого нажатия (просмотр деталей)
 * @param onLongClick Callback для обработки длинного нажатия (удаление результата)
 * @param modifier Модификатор для настройки внешнего вида карточки
 */
@Composable
fun PressureResultCard(
    result: SavedPressureCalcResult,
    onClick: (SavedPressureCalcResult) -> Unit,
    onLongClick: (SavedPressureCalcResult) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
            ),
        border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.primary),
        modifier =
            modifier
                .fillMaxWidth()
                .height(CARD_HEIGHT.dp)
                .combinedClickable(
                    onClick = { onClick(result) },
                    onLongClick = { onLongClick(result) },
                ),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                Column {
                    Text(
                        text = stringResource(Res.string.front_wheel),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.scrim,
                        maxLines = 1,
                        overflow = Ellipsis,
                    )
                    Text(
                        text = formatDoubleToString(result.pressureFront) + " " + stringResource(Res.string.bar),
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.scrim,
                        maxLines = 1,
                        overflow = Ellipsis,
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = stringResource(Res.string.rear_wheel),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.scrim,
                        maxLines = 1,
                        overflow = Ellipsis,
                    )
                    Text(
                        text = formatDoubleToString(result.pressureRear) + " " + stringResource(Res.string.bar),
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.scrim,
                        maxLines = 1,
                        overflow = Ellipsis,
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                Row {
                    Icon(
                        imageVector = LineAwesomeIcons.UserSolid,
                        contentDescription = null,
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = formatDoubleToString(result.riderWeight))
                }
                Row {
                    Icon(
                        imageVector = LineAwesomeIcons.BicycleSolid,
                        contentDescription = null,
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = formatDoubleToString(result.bikeWeight))
                }
                Row {
                    Icon(
                        imageVector = LineAwesomeIcons.CircleNotchSolid,
                        contentDescription = null,
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = result.wheelSize)
                }
                Row {
                    Icon(
                        imageVector = LineAwesomeIcons.ArrowsAltHSolid,
                        contentDescription = null,
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = result.tireSize)
                }
            }
        }
    }
}

@Preview
@Composable
private fun PressureResultCardPreview() {
    BikeCalcTheme {
        PressureResultCard(
            result =
                SavedPressureCalcResult(
                    id = 0,
                    pressureFront = 2.4,
                    pressureRear = 2.6,
                    riderWeight = 84.1,
                    bikeWeight = 11.9,
                    wheelSize = WheelSize.Inches29.inchesSize.toString(),
                    tireSize = TireSize29Inches.Size29x225.tireWidthInInches.toString(),
                ),
            onLongClick = {},
            onClick = {},
        )
    }
}
