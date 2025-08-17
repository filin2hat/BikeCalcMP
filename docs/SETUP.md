# 🚀 Установка и запуск

## 📋 Предварительные требования

### Обязательные компоненты

```bash
- JDK 17+
- Android Studio Hedgehog+ / IntelliJ IDEA 2023.3+
- Xcode 14+ (для iOS, только macOS)
```

### Рекомендуемые настройки

```bash
- Gradle 8.13+
- Kotlin 2.2.0+
- 8GB+ RAM для комфортной разработки
```

## 🏃‍♂️ Быстрый старт

### 1. Клонирование репозитория

```bash
git clone https://github.com/filin2hat/BikeCalcMP.git
cd BikeCalcMP
```

### 2. Запуск на разных платформах

#### 🖥️ Desktop (JVM)

```bash
# Запуск Desktop версии
./gradlew :composeApp:run

# Создание дистрибутива
./gradlew :composeApp:createDistributable
```

#### 🤖 Android

```bash
# Сборка Debug APK
./gradlew :composeApp:assembleDebug

# Сборка Release APK  
./gradlew :composeApp:assembleRelease

# Установка на подключенное устройство
./gradlew :composeApp:installDebug
```

#### 🍎 iOS (только macOS)

```bash
# Запуск в симуляторе
./gradlew :composeApp:iosSimulatorArm64Run

# Сборка для устройства
./gradlew :composeApp:iosArm64Run
```

## 📦 Модульная сборка

### Сборка всех модулей

```bash
# Параллельная сборка всех модулей
./gradlew build --parallel --build-cache

# Быстрая проверка компиляции
./gradlew compileKotlinMetadata --parallel
```

### Сборка отдельных модулей

```bash
# Core модули
./gradlew :core:model:build
./gradlew :core:common:build
./gradlew :core:database:build

# Domain модули
./gradlew :domain:pressure:build

# Data модули  
./gradlew :data:pressure:build

# Feature модули
./gradlew :feature:pressure:build

# Design system
./gradlew :designsystem:build
```

## 🧪 Тестирование

### Запуск тестов

```bash
# Все тесты
./gradlew test --parallel

# Тесты конкретного модуля
./gradlew :data:pressure:test

# Тесты с отчетом покрытия
./gradlew test jacocoTestReport
```

### Статический анализ

```bash
# Detekt анализ
./gradlew detekt

# Просмотр отчетов
open build/reports/detekt/detekt.html
```

## 🛠️ Разработка

### Настройка IDE

#### Android Studio / IntelliJ IDEA

1. Импортировать проект как Gradle проект
2. Убедиться что Kotlin plugin актуальной версии
3. Включить Compose preview
4. Настроить Detekt plugin для real-time анализа

#### Xcode (для iOS)

1. Открыть `iosApp/iosApp.xcodeproj`
2. Убедиться что Command Line Tools установлены
3. Выбрать правильную команду разработки

### Полезные команды для разработки

```bash
# Очистка проекта
./gradlew clean

# Пересборка с очисткой кэша
./gradlew clean build --no-build-cache

# Проверка зависимостей
./gradlew :composeApp:dependencies

# Анализ времени сборки
./gradlew build --profile
```

## 🔧 Устранение проблем

### Частые проблемы

#### 1. "Gradle sync failed"

```bash
# Очистка Gradle кэша
./gradlew clean
rm -rf ~/.gradle/caches

# Пересинхронизация
./gradlew --refresh-dependencies
```

#### 2. "iOS build failed"

```bash
# Проверка Xcode
xcode-select --print-path

# Переустановка Command Line Tools
sudo xcode-select --install
```

#### 3. "KSP compilation error"

```bash
# Очистка KSP кэша
./gradlew clean
./gradlew :core:database:kspKotlinMetadata
```

#### 4. "Out of memory error"

```bash
# Увеличение памяти для Gradle
export GRADLE_OPTS="-Xmx4g -Dfile.encoding=UTF-8"
```

### Проверка окружения

```bash
# Версия Java
java -version

# Версия Gradle
./gradlew --version

# Версия Kotlin
./gradlew :composeApp:kotlinVersion

# Доступные задачи
./gradlew tasks --all
```

## 📱 Платформенная специфика

### Android

- Минимальная SDK: 24 (Android 7.0)
- Target SDK: 36 (Android 14)
- Поддержка: телефоны, планшеты, складные устройства

### iOS

- Минимальная версия: iOS 14.0
- Поддержка: iPhone, iPad
- Архитектуры: ARM64, x64 (симулятор)

### Desktop

- JVM 17+
- Поддержка: Windows, macOS, Linux
- Упаковка: MSI, DMG, DEB

## 🎯 Быстрая проверка

После клонирования проекта, выполните:

```bash
# Базовая проверка
./gradlew compileKotlinMetadata

# Если все ОК, запустите Desktop версию
./gradlew :composeApp:run
```

При успешном выполнении этих команд проект готов к разработке! 🎉

## 📊 Настройка Kotzilla Analytics

### 🔍 Что такое Kotzilla

Kotzilla - это система мониторинга производительности для Kotlin приложений с Koin. Она
автоматически отслеживает производительность и помогает выявлять проблемы.

### ⚙️ Автоматическая настройка

Kotzilla уже настроен в проекте и работает автоматически:

```bash
# Проверка конфигурации
cat composeApp/kotzilla.json

# Проверка сгенерированного ключа
ls -la composeApp/src/main/assets/kotzilla.key
```

### 🔧 Ручная настройка (если нужно)

#### 1. Очистка и пересборка

```bash
# Очистка старого ключа
./gradlew clean

# Генерация нового ключа
./gradlew :composeApp:assembleDebug
```

#### 2. Проверка инициализации

```bash
# Запуск приложения с логами
./gradlew :composeApp:run

# В логах должно появиться:
# ✅ Kotzilla analytics успешно инициализирован
```

#### 3. Устранение проблем

```bash
# Если ключ не генерируется
./gradlew :composeApp:generateAndroidAssetsKey

# Если есть ошибки инициализации
./gradlew :composeApp:assembleDebug --info
```

### 📈 Что отслеживается

- **Время запуска** приложения
- **Производительность** разрешения зависимостей Koin
- **Стабильность** на разных платформах
- **Метрики** модульной архитектуры

### 📖 Подробная документация

См. [Kotzilla.md](./KOTZILLA.md) для полной документации по настройке и использованию.
