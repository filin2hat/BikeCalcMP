package dev.filinhat.bikecalc.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import dev.filinhat.bikecalc.designsystem.theme.BikeCalcTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun InputCard(
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    value: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
) {
    var inputValue by remember { mutableStateOf(value) }

    ElevatedCard(
        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 8.dp,
            ),
        modifier =
            modifier
                .fillMaxWidth(),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(8.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            OutlinedTextField(
                label = {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                },
                value = inputValue,
                onValueChange = { newValue ->
                    inputValue = newValue
                    onValueChange(newValue)
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
                modifier = Modifier.fillMaxWidth(),
                colors =
                    OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.background,
                        unfocusedContainerColor = MaterialTheme.colorScheme.background,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                        focusedTextColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.inversePrimary,
                    ),
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Preview
@Composable
private fun InputCardPreview() {
    BikeCalcTheme {
        InputCard(
            onValueChange = {},
            label = "Sample Label",
            value = "Sample Value",
        )
    }
}