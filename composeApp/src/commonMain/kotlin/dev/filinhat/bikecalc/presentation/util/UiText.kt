package dev.filinhat.bikecalc.presentation.util

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

/**
 * Интерфейс, представляющий текст, который может быть отображён в UI.
 *
 * Этот интерфейс поддерживает как динамические строки, так и строки из ресурсов.
 */
sealed interface UiText {
    /**
     * Класс для представления динамической строки.
     *
     * @property value Текст строки.
     */
    data class DynamicString(
        val value: String,
    ) : UiText

    /**
     * Класс для представления строки из ресурсов.
     *
     * @property id Идентификатор строки из ресурсов.
     * @property args Аргументы для форматирования строки (по умолчанию пустой массив).
     */
    class StringResourceId(
        val id: StringResource,
        val args: Array<Any> = arrayOf(),
    ) : UiText

    /**
     * Преобразует экземпляр [UiText] в строку для отображения в интерфейсе.
     *
     * Эта функция использует аннотацию [Composable] для доступа к ресурсам строки в Compose.
     *
     * @return Текст в виде строки.
     */
    @Composable
    fun asString(): String =
        when (this) {
            is DynamicString -> value
            is StringResourceId -> stringResource(resource = id, formatArgs = args)
        }
}
