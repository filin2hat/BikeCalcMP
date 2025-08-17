# 🏗️ Архитектура BikeCalc Pro

## 📱 Android-специфичные особенности

### 🎨 Иконки и названия приложения

- **Production версия**:
    - Иконка: Желтый фон (#E7C349) с велосипедной иконкой
    - Название: "BikeCalc Pro" / "ВелоКалькулятор Pro"
- **Debug версия**:
    - Иконка: Красный фон (#FF4444) с велосипедной иконкой и красной буквой "D" в правом верхнем
      углу
    - Название: "BikeCalc Pro [DEBUG]" / "ВелоКалькулятор Pro [ОТЛАДКА]"
- **Расположение**:
    - Production: `composeApp/src/androidMain/res/`
    - Debug: `composeApp/src/debug/res/`

## 📐 Модульная структура

```
🏗️ BikeCalc Pro - Многомодульная KMP архитектура
│
├── 📱 composeApp/          # Главный модуль приложения
│   ├── app/                # Точка входа и навигация
│   ├── di/                 # Центральная настройка DI
│   └── platform-specific/  # Android, iOS, Desktop setup
│
├── 🏗️ core/               # Основная инфраструктура  
│   ├── model/              # Модели данных и енумы
│   ├── common/             # Утилиты и базовые типы
│   └── database/           # Room, DAO, Entity
│
├── 🎯 domain/pressure/     # Бизнес-логика (Pressure)
│   ├── repository/         # Абстракции доступа к данным  
│   ├── service/            # Бизнес-сервисы
│   └── usecase/            # Use cases (интерфейсы + impl)
│
├── 🎯 domain/development/  # Бизнес-логика (Development)
│   ├── repository/         # Абстракции доступа к данным
│   ├── service/            # Бизнес-сервисы
│   └── usecase/            # Use cases (интерфейсы + impl)
│
├── 💾 data/pressure/       # Реализация данных (Pressure)
│   ├── repository/         # Конкретные репозитории
│   └── service/            # Реализация бизнес-логики
│
├── 💾 data/development/    # Реализация данных (Development)
│   ├── repository/         # Конкретные репозитории
│   └── service/            # Реализация бизнес-логики
│
├── 🎨 feature/pressure/    # UI модуль функции (Pressure)
│   ├── screen/             # Экраны
│   ├── component/          # UI компоненты
│   ├── viewmodel/          # ViewModels
│   └── state/              # State management
│
├── 🎨 feature/development/ # UI модуль функции (Development)
│   ├── screen/             # Экраны
│   ├── component/          # UI компоненты
│   ├── viewmodel/          # ViewModels
│   └── state/              # State management
│
└── 🎪 designsystem/        # Дизайн-система
    ├── theme/              # Темы, цвета, типографика
    └── component/          # Переиспользуемые UI компоненты
```

## 🔄 Принципы архитектуры

- **📊 Четкое разделение слоев**: Domain ← Data ← Feature → DesignSystem, Core
- **🔗 Инверсия зависимостей**: Зависимости направлены к абстракциям
- **🧩 Модульная DI**: Каждый модуль управляет своими зависимостями
- **⚡ Параллельная сборка**: Модули компилируются независимо
- **📦 Переиспользование**: Core модули используются во всех feature

## 🛠️ Технологический стек

### 🎨 Frontend & UI

- **[Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)** - Декларативный
  UI
- **[Material Design 3](https://m3.material.io/)** - Современный дизайн
- **[Navigation Compose](https://developer.android.com/jetpack/compose/navigation)** -
  Типобезопасная навигация

### 🏛️ Архитектура & DI

-
    *
*[Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
** - Слоистая архитектура
- **[MVVM](https://developer.android.com/topic/architecture)** - Презентационный слой
- **[Koin](https://insert-koin.io/)** - Dependency Injection

### 💾 Данные & Персистентность

- **[Room](https://developer.android.com/training/data-storage/room)** - Локальная база данных
- **[SQLite](https://www.sqlite.org/)** - Движок базы данных
- **[Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization)** - Сериализация

### ⚙️ Асинхронность & Реактивность

- **[Kotlinx Coroutines](https://github.com/Kotlin/kotlinx.coroutines)** - Асинхронное
  программирование
- **[Flow](https://kotlinlang.org/docs/flow.html)** - Реактивные потоки данных

### 🔧 Инструменты разработки

- **[Gradle Version Catalogs](https://docs.gradle.org/current/userguide/platforms.html)** -
  Управление зависимостями
- **[KSP](https://github.com/google/ksp)** - Обработка аннотаций
- **expect/actual** - Платформенно-специфический код

## 📈 Производительность

### ⚡ Метрики сборки

- **Время полной сборки**: ~45 сек (8 модулей)
- **Инкрементальная сборка**: ~5-10 сек
- **Parallel compilation**: до 4x ускорение
- **Кэширование Gradle**: 80%+ cache hits

### 📊 Метрики кода

- **Общий код**: 95%+ между платформами
- **Покрытие тестами**: Domain layer 90%+
- **Цикломатическая сложность**: <10 для критических методов

## 📊 Мониторинг производительности

### 🔍 Kotzilla Analytics

Проект использует **[Kotzilla](https://kotzilla.io/)** для мониторинга производительности:

- **📈 Визуализация графа зависимостей** Koin в Android Studio/IntelliJ
- **🚀 Отслеживание времени запуска** приложения
- **🐛 Профилирование разрешения компонентов** во время разработки
- **📱 Мониторинг производительности** на всех устройствах в продакшене
- **🔄 Автоматическое отслеживание** без ручной инструментации

**📖 Подробная документация**: [Kotzilla.md](./KOTZILLA.md)

### 🔧 Конфигурация

- **Файл конфигурации**: `composeApp/kotzilla.json`
- **Автоматическая генерация ключей**: `composeApp/src/main/assets/kotzilla.key`
- **Инициализация**: `composeApp/src/commonMain/kotlin/dev/filinhat/bikecalc/di/InitKoin.kt`

### 📊 Что отслеживается

- **Время инициализации** Koin контейнера
- **Разрешение зависимостей** и их производительность
- **Время запуска** приложения на разных платформах
- **Стабильность** в продакшене на реальных устройствах
- **Производительность** модульной архитектуры
