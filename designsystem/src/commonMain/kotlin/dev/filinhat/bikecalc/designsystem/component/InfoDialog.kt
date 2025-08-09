package dev.filinhat.bikecalc.designsystem.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * Информационное окно.
 *
 * @param onCloseDialog функция закрытия диалога
 * @param dialogTitle заголовок диалога
 * @param dialogText текст диалога
 * @param icon иконка
 * @param buttonText текст кнопки
 */
@Composable
fun InfoDialog(
    onCloseDialog: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
    buttonText: String = "Понятно",
) {
    AlertDialog(
        icon = {
            Icon(
                icon,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.primary,
                modifier =
                    Modifier
                        .rotate(180f)
                        .size(36.dp),
            )
        },
        title = {
            Text(
                text = dialogTitle,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
            )
        },
        text = {
            Column(
                modifier =
                    Modifier
                        .verticalScroll(rememberScrollState()),
            ) {
                Text(
                    text = dialogText,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onCloseDialog()
                },
            ) {
                Text(buttonText)
            }
        },
        onDismissRequest = { },
    )
}


