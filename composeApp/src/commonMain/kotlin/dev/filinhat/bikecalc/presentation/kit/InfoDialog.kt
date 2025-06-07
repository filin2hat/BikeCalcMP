package dev.filinhat.bikecalc.presentation.kit

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
import androidx.compose.ui.unit.dp
import bikecalcmp.composeapp.generated.resources.Res
import bikecalcmp.composeapp.generated.resources.i_understand_this
import org.jetbrains.compose.resources.stringResource

/**
 * Информационное окно.
 *
 * @param onCloseDialog функция закрытия диалога
 * @param dialogTitle заголовок диалога
 * @param dialogText текст диалога
 * @param icon иконка
 */
@Composable
fun InfoDialog(
    onCloseDialog: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
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
                Text(stringResource(Res.string.i_understand_this))
            }
        },
        onDismissRequest = { },
    )
}
