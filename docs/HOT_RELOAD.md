# 🔥 Compose Hot Reload

**Compose Hot Reload** — это инструмент от JetBrains для мгновенного обновления UI в Compose Multiplatform приложениях без перезапуска. Позволяет видеть изменения в интерфейсе сразу после правки кода, значительно ускоряя процесс разработки.

## 📋 Пошаговое подключение к проекту

### Шаг 1: Обновление settings.gradle.kts

Добавьте Foojay resolver для автоматической настройки JetBrains Runtime:

```kotlin
// settings.gradle.kts
pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
                includeGroupByRegex("android.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
    }
}
```

### Шаг 2: Обновление gradle/libs.versions.toml

Добавьте версию плагина и сам плагин:

```toml
[versions]
# ... существующие версии ...
compose-hot-reload = "1.0.0-beta05"

[plugins]
# ... существующие плагины ...
compose-hot-reload = { id = "org.jetbrains.compose.hot-reload", version.ref = "compose-hot-reload" }
```

### Шаг 3: Обновление composeApp/build.gradle.kts

Добавьте плагин в основной модуль приложения:

```kotlin
// composeApp/build.gradle.kts
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose.hot.reload)  // ← Добавьте эту строку
    alias(libs.plugins.jetbrains.kotlin.serialization)
    // ... другие плагины
}

// mainClass уже должен быть настроен в compose.desktop.application
compose.desktop {
    application {
        mainClass = "dev.filinhat.bikecalc.MainKt"
        // ... остальная конфигурация
    }
}
```

### Шаг 4: Проверка настройки

Убедитесь, что задачи hot reload доступны:

```bash
./gradlew :composeApp:tasks --all | findstr -i "hot"
```

Должны появиться задачи:
- `hotRunDesktopAsync`
- `hotRunDesktop`
- `reload`
- и другие

### Шаг 5: Запуск с Hot Reload

```bash
./gradlew :composeApp:hotRunDesktopAsync --autoReload
```

### Шаг 6: Тестирование

1. Откройте любой UI файл (например, `feature/pressure/component/PressureCalcCard.kt`)
2. Измените текст или стили
3. Сохраните файл (Ctrl+S)
4. UI обновится автоматически!

## ✅ Готово!

Теперь Compose Hot Reload подключен к вашему проекту и готов к использованию!

## 📋 Требования и совместимость

### Минимальные требования
- ✅ **Kotlin 2.1.20+** (рекомендуется 2.2.0+)
- ✅ **Compose Multiplatform 1.8.0+**
- ✅ **Desktop target** в проекте
- ✅ **Gradle 8.0+**

### Проверка совместимости

Убедитесь, что ваш проект соответствует требованиям:

```bash
# Проверка версии Kotlin
./gradlew --version

# Проверка доступности desktop target
./gradlew :composeApp:tasks --group="compose desktop"

# Проверка hot reload задач
./gradlew :composeApp:tasks --all | findstr -i "hot"
```

### Совместимость с архитектурой

Compose Hot Reload полностью совместим с:
- ✅ **Модульной архитектурой** (core, domain, data, feature модули)
- ✅ **Clean Architecture** и MVVM
- ✅ **Koin DI** и другими DI фреймворками
- ✅ **Compose Navigation**
- ✅ **Material Design 3** темами

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

## 🎨 Примеры использования в BikeCalcMP

### Пример 1: Изменение UI компонента

**Файл:** `feature/pressure/component/PressureCalcCard.kt`

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

### Пример 2: Изменение ViewModel

**Файл:** `feature/pressure/viewmodel/PressureCalculatorViewModel.kt`

```kotlin
// Измените логику или состояния
private fun calculatePressure() {
    viewModelScope.launch {
        // Новая логика расчета
        val result = pressureCalculationService.calculate(params)
        _state.update { it.copy(result = result) }
    }
}
// Сохраните файл - изменения применятся к UI
```

### Пример 3: Изменение темы

**Файл:** `designsystem/theme/Colors.kt`

```kotlin
// Измените цвета темы
val md_theme_light_primary = Color(0xFF006C4C) // ← Измените цвет
val md_theme_dark_primary = Color(0xFF67DBB0)  // ← Измените цвет
// Сохраните файл - тема обновится автоматически
```

### Пример 4: Изменение навигации

**Файл:** `app/navigation/BikeCalcRoute.kt`

```kotlin
// Добавьте новый экран
object NewFeature : BikeCalcRoute("new_feature")
// Сохраните файл - навигация обновится
```

### Пример 5: Изменение компонента с состоянием

**Файл:** `feature/pressure/component/CalculatePressureButton.kt`

```kotlin
// Измените логику кнопки
Button(
    onClick = {
        // Новая логика
        viewModel.calculatePressure()
    },
    enabled = state.isValid // ← Измените условие
) {
    Text("Рассчитать давление")
}
// Сохраните файл - кнопка обновится
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

### ❌ Ошибка: "Unresolved reference: hot"

**Проблема:** Плагин не загружается правильно.

**Решение:**
1. Проверьте правильность добавления плагина в `libs.versions.toml`
2. Убедитесь, что версия указана корректно
3. Выполните `./gradlew clean` и перезапустите IDE

### ❌ Ошибка: "Missing androidTarget()"

**Проблема:** Android target не настроен в Kotlin Multiplatform.

**Решение:**
```kotlin
// composeApp/build.gradle.kts
kotlin {
    androidTarget() // ← Добавьте эту строку
    jvm("desktop")
    // ... другие targets
}
```

### ❌ Приложение не запускается

**Решение:**
```bash
# Очистить и пересобрать
./gradlew clean
./gradlew :composeApp:hotRunDesktopAsync --autoReload
```

### ❌ Hot Reload не работает

**Возможные причины и решения:**
- **Не используется `--autoReload`** → Добавьте флаг
- **Файл не сохранен** → Сохраните файл (Ctrl+S)
- **Изменился main()** → Перезапустите приложение
- **Добавлены новые зависимости** → Перезапустите приложение

### ❌ Медленная работа

**Оптимизации:**
- Используйте `hotRunDesktopAsync` вместо `hotRunDesktop`
- Закройте неиспользуемые файлы в IDE
- Избегайте частых изменений в больших файлах
- Используйте SSD для проекта

### ❌ Ошибка: "JetBrains Runtime not found"

**Решение:**
1. Убедитесь, что Foojay resolver добавлен в `settings.gradle.kts`
2. Выполните `./gradlew clean`
3. Перезапустите IDE

### ❌ Задачи hot reload не найдены

**Решение:**
1. Проверьте, что плагин добавлен в `composeApp/build.gradle.kts`
2. Убедитесь, что есть desktop target
3. Выполните `./gradlew :composeApp:tasks --all | findstr -i "hot"`

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
