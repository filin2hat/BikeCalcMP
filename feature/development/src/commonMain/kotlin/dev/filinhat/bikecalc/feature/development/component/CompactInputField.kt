package dev.filinhat.bikecalc.feature.development.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import dev.filinhat.bikecalc.core.common.util.isAllowedPositiveIntCsvInput
import dev.filinhat.bikecalc.core.common.util.isAllowedSinglePositiveIntInput
import dev.filinhat.bikecalc.core.common.util.validatePositiveIntCsv
import dev.filinhat.bikecalc.core.common.util.validateSinglePositiveInt
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CompactInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Number,
    allowMultipleValues: Boolean = false,
) {
    var wrongNumericInput by rememberSaveable { mutableStateOf(false) }
    var internalValue by rememberSaveable { mutableStateOf(value) }

    val minValue = 1
    val maxValue = 1000

    // Синхронизация и восстановление значения между внешним `value` и локальным сохранённым `internalValue`.
    LaunchedEffect(value) {
        if (value != internalValue) {
            if (value.isNotEmpty()) {
                internalValue = value
            } else if (internalValue.isNotEmpty()) {
                onValueChange(internalValue)
            } else {
                internalValue = value
            }
        }
    }

    LaunchedEffect(internalValue, allowMultipleValues) {
        wrongNumericInput =
            if (internalValue.isEmpty() || internalValue.isBlank()) {
                false
            } else {
                if (!allowMultipleValues) {
                    !validateSinglePositiveInt(internalValue, minValue, maxValue)
                } else {
                    !validatePositiveIntCsv(internalValue, minValue, maxValue)
                }
            }
    }

    OutlinedTextField(
        value = internalValue,
        onValueChange = { newValue ->
            // Фильтрация допустимых символов согласно режиму (промежуточный ввод)
            if (newValue.isEmpty()) {
                internalValue = ""
                onValueChange("")
                return@OutlinedTextField
            }
            val allowed =
                if (allowMultipleValues) {
                    isAllowedPositiveIntCsvInput(newValue)
                } else {
                    isAllowedSinglePositiveIntInput(newValue)
                }
            if (!allowed) {
                return@OutlinedTextField
            }
            internalValue = newValue
            onValueChange(newValue)
        },
        label = { Text(text = label, style = MaterialTheme.typography.labelSmall) },
        textStyle = MaterialTheme.typography.bodyMedium,
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
        modifier = modifier,
        isError = wrongNumericInput,
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
    )
}

@Preview
@Composable
private fun CompactInputFieldPreview() {
    val (text, setText) = remember { mutableStateOf("") }
    CompactInputField(
        value = text,
        onValueChange = setText,
        label = "Sample Label",
        modifier = Modifier.fillMaxWidth(),
    )
}
