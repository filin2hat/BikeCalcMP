package dev.filinhat.openlibapp.core.domain

/**
 * Обобщённый класс-обёртка для представления результата выполнения операции.
 *
 * @param D Тип данных в случае успешного результата.
 * @param E Тип ошибки в случае неудачи. Должен быть подклассом [Error].
 */
sealed interface Result<out D, out E : Error> {
    /**
     * Класс для представления успешного результата.
     *
     * @param D Тип данных результата.
     * @property data Данные успешного результата.
     */
    data class Success<out D>(
        val data: D,
    ) : Result<D, Nothing>

    /**
     * Класс для представления ошибки.
     *
     * @param E Тип ошибки, должен быть подклассом [Error].
     * @property error Детали ошибки.
     */
    data class Error<out E : dev.filinhat.openlibapp.core.domain.Error>(
        val error: E,
    ) : Result<Nothing, E>
}

/**
 * Преобразует успешный результат в новый результат с другим типом данных, применяя заданную функцию.
 *
 * @param map Функция преобразования данных.
 * @return Новый результат с преобразованными данными или той же ошибкой.
 */
inline fun <T, E : Error, R> Result<T, E>.map(map: (T) -> R): Result<R, E> =
    when (this) {
        is Result.Error -> Result.Error(error)
        is Result.Success -> Result.Success(map(data))
    }

/**
 * Преобразует результат в пустой результат [EmptyResult], игнорируя данные.
 *
 * @return Пустой результат [EmptyResult] с той же ошибкой или успехом.
 */
fun <T, E : Error> Result<T, E>.asEmptyDataResult(): EmptyResult<E> = map { }

/**
 * Выполняет заданное действие, если результат успешный.
 *
 * @param action Действие, выполняемое с данными успешного результата.
 * @return Исходный результат, чтобы поддерживать цепочку вызовов.
 */
inline fun <T, E : Error> Result<T, E>.onSuccess(action: (T) -> Unit): Result<T, E> =
    when (this) {
        is Result.Error -> this
        is Result.Success -> {
            action(data)
            this
        }
    }

/**
 * Выполняет заданное действие, если результат содержит ошибку.
 *
 * @param action Действие, выполняемое с данными ошибки.
 * @return Исходный результат, чтобы поддерживать цепочку вызовов.
 */
inline fun <T, E : Error> Result<T, E>.onError(action: (E) -> Unit): Result<T, E> =
    when (this) {
        is Result.Error -> {
            action(error)
            this
        }

        is Result.Success -> this
    }

/**
 * Тип, представляющий пустой успешный результат или ошибку.
 *
 * @param E Тип ошибки.
 */
typealias EmptyResult<E> = Result<Unit, E>
