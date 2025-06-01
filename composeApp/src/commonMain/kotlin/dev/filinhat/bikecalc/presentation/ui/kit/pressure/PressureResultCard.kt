package dev.filinhat.bikecalc.presentation.ui.kit.pressure

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.unit.dp
import bikecalcmp.composeapp.generated.resources.Res
import bikecalcmp.composeapp.generated.resources.bar
import bikecalcmp.composeapp.generated.resources.front_wheel
import bikecalcmp.composeapp.generated.resources.rear_wheel
import compose.icons.LineAwesomeIcons
import compose.icons.lineawesomeicons.ArrowsAltHSolid
import compose.icons.lineawesomeicons.BicycleSolid
import compose.icons.lineawesomeicons.CircleNotchSolid
import compose.icons.lineawesomeicons.UserSolid
import dev.filinhat.bikecalc.domain.enums.tire.TireSize29Inches
import dev.filinhat.bikecalc.domain.enums.wheel.WheelSize
import dev.filinhat.bikecalc.domain.model.SavedPressureCalcResult
import dev.filinhat.bikecalc.presentation.ui.theme.BikeCalcTheme
import dev.filinhat.bikecalc.presentation.util.formatValue
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

private const val CARD_HEIGHT = 110

@Composable
fun PressureResultCard(
    result: SavedPressureCalcResult,
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
                .height(CARD_HEIGHT.dp),
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
                    )
                    Text(
                        text = formatValue(result.pressureFront) + " " + stringResource(Res.string.bar),
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.scrim,
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = stringResource(Res.string.rear_wheel),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.scrim,
                    )
                    Text(
                        text = formatValue(result.pressureRear) + " " + stringResource(Res.string.bar),
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.scrim,
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
                    Text(text = formatValue(result.riderWeight))
                }
                Row {
                    Icon(
                        imageVector = LineAwesomeIcons.BicycleSolid,
                        contentDescription = null,
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = formatValue(result.bikeWeight))
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
                    pressureFront = 2.4,
                    pressureRear = 2.6,
                    riderWeight = 84.1,
                    bikeWeight = 11.9,
                    wheelSize = WheelSize.Inches29.inchesSize.toString(),
                    tireSize = TireSize29Inches.Size29x225.tireWidthInInches.toString(),
                ),
        )
    }
}
