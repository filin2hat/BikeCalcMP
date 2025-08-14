# 🔥 Compose Hot Reload

**Compose Hot Reload** — это инструмент от JetBrains для мгновенного обновления UI в Compose Multiplatform приложениях без перезапуска. Позволяет видеть изменения в интерфейсе сразу после правки кода, значительно ускоряя процесс разработки.

## ⚡ Быстрый старт

### Запуск приложения с Hot Reload

```bash
# Рекомендуемый способ (асинхронный)
./gradlew :composeApp:hotRunDesktopAsync --autoReload

# Альтернативный способ
./gradlew :composeApp:hotRunDesktop --autoReload
```

### Тестирование Hot Reload

1. **Запустите приложение** одной из команд выше
2. **Откройте любой UI файл** (например, компонент в `feature/pressure`)
3. **Измените код** (текст, цвета, размеры)
4. **Сохраните файл** (Ctrl+S)
5. **Наблюдайте** как UI обновляется автоматически!

## 🛠️ Доступные команды

```bash
# Запуск с автоматической перезагрузкой
./gradlew :composeApp:hotRunDesktopAsync --autoReload

# Запуск без автоматической перезагрузки
./gradlew :composeApp:hotRunDesktopAsync --no-autoReload

# Ручная перезагрузка (если --no-autoReload)
./gradlew :composeApp:reload

# Просмотр всех hot reload задач
./gradlew :composeApp:tasks --all | findstr -i "hot"
```

## 📝 Что можно изменять

### ✅ Поддерживается
- UI компоненты Compose
- Тексты и стили
- Цвета и размеры
- Логика ViewModels
- Состояния приложения
- Навигация
- Темы и стили

### ❌ Не поддерживается
- Изменения в `main()` функции
- Добавление новых зависимостей
- Изменения в Gradle файлах
- Модификация плагинов

## 🎨 Примеры использования

### Изменение UI компонента

```kotlin
// До изменения
Text(
    text = "Калькулятор давления",
    style = MaterialTheme.typography.headlineMedium
)

// После изменения - сохраните файл и UI обновится автоматически
Text(
    text = "🔥 Калькулятор давления",
    style = MaterialTheme.typography.headlineMedium,
    color = MaterialTheme.colorScheme.primary
)
```

### Изменение ViewModel

```kotlin
// В PressureCalculatorViewModel.kt
// Измените логику или состояния
private fun calculatePressure() {
    // Новая логика расчета
    viewModelScope.launch {
        // Обновленная логика
    }
}
// Сохраните файл - изменения применятся к UI
```

## 🔧 Конфигурация

### Автоматическая настройка
- ✅ **JetBrains Runtime** настраивается автоматически через Foojay resolver
- ✅ **mainClass** берется из `compose.desktop.application`
- ✅ **Плагин** автоматически создает необходимые Gradle задачи

### Требования
- ✅ Kotlin 2.2.0 (требуется 2.1.20+)
- ✅ Compose Multiplatform 1.8.2
- ✅ Desktop target настроен

## 🚀 Преимущества

- ⚡ **Мгновенное обновление UI** без перезапуска приложения
- 🔄 **Сохранение состояния** приложения при изменениях
- 🎯 **Фокус на UI/UX** без отвлечений на перезапуск
- 🚀 **Ускорение итеративного процесса** разработки
- 🎨 **Поддержка всех Compose компонентов** и ViewModels

## 🆘 Устранение проблем

### Приложение не запускается
```bash
./gradlew clean
./gradlew :composeApp:hotRunDesktopAsync --autoReload
```

### Hot Reload не работает
- Убедитесь, что используется `--autoReload`
- Проверьте, что файл сохранен (Ctrl+S)
- Перезапустите приложение

### Медленная работа
- Используйте `hotRunDesktopAsync` вместо `hotRunDesktop`
- Закройте неиспользуемые файлы в IDE
- Избегайте частых изменений в больших файлах

## 📚 Дополнительные ресурсы

- [Официальная документация](https://github.com/JetBrains/compose-hot-reload)
- [Примеры использования](https://github.com/JetBrains/compose-hot-reload/tree/master/examples)
- [FAQ](https://github.com/JetBrains/compose-hot-reload#faq)

## 🎯 Интеграция в рабочий процесс

### Рекомендуемый workflow
1. **Запустите** приложение с `--autoReload`
2. **Работайте** над UI компонентами
3. **Сохраняйте** файлы для мгновенного обновления
4. **Тестируйте** изменения в реальном времени
5. **Перезапускайте** только при необходимости (новые зависимости, main() изменения)

### Советы по производительности
- Используйте `hotRunDesktopAsync` для лучшей производительности
- Включайте `--autoReload` для автоматического обновления
- Закрывайте неиспользуемые файлы в IDE
- Избегайте частых изменений в больших файлах
