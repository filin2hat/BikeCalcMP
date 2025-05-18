package dev.filinhat.bikecalc.presentation.ui.kit.pressure

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.filinhat.bikecalc.domain.model.SavedPressureCalcResult
import dev.filinhat.bikecalc.presentation.ui.theme.BikeCalcTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun PressureResultCard(
    result: SavedPressureCalcResult,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(text = result.pressureFront.toString())
            Text(text = result.pressureRear.toString())
            Column {
                Text(text = result.riderWeight.toString())
                Text(text = result.bikeWeight.toString())
                Text(text = result.wheelSize)
                Text(text = result.tireSize)
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
                    wheelSize = "29",
                    tireSize = "2.25",
                ),
        )
    }
}
