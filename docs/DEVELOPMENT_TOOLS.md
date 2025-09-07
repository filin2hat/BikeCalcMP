# 🛠️ Инструменты разработки BikeCalc Pro

## 📋 Обзор инструментов

BikeCalc Pro использует современный набор инструментов для обеспечения качества кода, производительности и удобства разработки.

## 🔍 Обнаружение утечек памяти - LeakCanary

### 🎯 Назначение

**[LeakCanary](https://square.github.io/leakcanary/)** автоматически обнаруживает утечки памяти в Android приложении во время разработки.

### ⚙️ Конфигурация

```toml
# gradle/libs.versions.toml
leakcanary = "2.14"
leakcanary-android = { module = "com.squareup.leakcanary:leakcanary-android", version.ref = "leakcanary" }
```

```kotlin
// composeApp/build.gradle.kts
dependencies {
    debugImplementation(libs.leakcanary.android)
}
```

### 🚀 Использование

1. **Автоматический запуск** - работает в debug сборках без дополнительной настройки
2. **Мониторинг компонентов** - отслеживает Activity, Fragment, ViewModel, Service
3. **Уведомления** - показывает уведомления при обнаружении утечек
4. **Детальные отчеты** - предоставляет трассировку объектов

### 📊 Мониторируемые компоненты

- **Android**: Activity, Fragment, Service, BroadcastReceiver
- **Compose**: ViewModel, CompositionLocal, State объекты  
- **Koin**: Singleton и Scoped компоненты
- **Coroutines**: Job, Flow, StateFlow коллекторы

### 🔧 Команды

```bash
# Сборка с LeakCanary
./gradlew :composeApp:assembleDebug

# Установка debug версии
./gradlew :composeApp:installDebug

# Проверка логов LeakCanary
adb logcat | grep LeakCanary
```

## 📊 Мониторинг производительности - Kotzilla

### 🎯 Назначение

**[Kotzilla](https://kotzilla.io/)** обеспечивает мониторинг производительности Koin DI контейнера и общей производительности приложения.

### ⚙️ Функции

- **📈 Визуализация графа зависимостей** в IDE
- **🚀 Отслеживание времени запуска** приложения
- **🔍 Профилирование разрешения компонентов**
- **📱 Мониторинг в продакшене**

### 📖 Документация

Подробная информация: [Kotzilla.md](./KOTZILLA.md)

## 🔍 Статический анализ кода - Detekt

### 🎯 Назначение

**[Detekt](https://detekt.dev/)** выполняет статический анализ Kotlin кода для выявления потенциальных проблем.

### 🔧 Команды

```bash
# Запуск анализа
./gradlew detekt

# Просмотр отчетов
open build/reports/detekt/detekt.html

# Автоисправление (где возможно)
./gradlew detektBaseline
```

### ⚙️ Конфигурация

- **Файл конфигурации**: `detekt.yml`
- **Правила**: Code smell detection, complexity analysis, formatting
- **Интеграция**: CI/CD pipeline, IDE plugin

## 🏗️ Система сборки - Gradle

### 📦 Version Catalogs

**[Gradle Version Catalogs](https://docs.gradle.org/current/userguide/platforms.html)** обеспечивает централизованное управление зависимостями.

#### 📁 Структура

```toml
# gradle/libs.versions.toml
[versions]
leakcanary = "2.14"
detekt = "1.23.8"

[libraries]
leakcanary-android = { module = "com.squareup.leakcanary:leakcanary-android", version.ref = "leakcanary" }
detekt-formatting = { module = "io.gitlab.arturbosch.detekt:detekt-formatting", version.ref = "detekt" }

[plugins]
detekt = { id = "io.gitlab.arturbosch.detekt", version.ref = "detekt" }
```

#### 🚀 Преимущества

- **🎯 Централизованное управление** версиями зависимостей
- **🔄 Типобезопасные ссылки** на зависимости
- **📊 Легкое обновление** версий
- **🛡️ Предотвращение конфликтов** версий

### 🔧 Полезные команды

```bash
# Проверка зависимостей
./gradlew :composeApp:dependencies

# Анализ времени сборки
./gradlew build --profile

# Параллельная сборка
./gradlew build --parallel --build-cache

# Очистка проекта
./gradlew clean
```

## 🔄 Обработка аннотаций - KSP

### 🎯 Назначение

**[KSP (Kotlin Symbol Processing)](https://github.com/google/ksp)** используется для обработки аннотаций, в частности для Room database.

### ⚙️ Использование

```kotlin
// Генерация DAO и Database классов
@Database(entities = [PressureResultEntity::class], version = 1)
abstract class PressureResultsDatabase : RoomDatabase()

@Dao
interface PressureResultsDao {
    @Query("SELECT * FROM pressure_results")
    fun getAllResults(): Flow<List<PressureResultEntity>>
}
```

### 🔧 Команды

```bash
# Принудительная регенерация
./gradlew :core:database:kspKotlinMetadata

# Очистка KSP кэша
./gradlew clean
```

## 🌐 Платформенная специфика - expect/actual

### 🎯 Назначение

**expect/actual** механизм позволяет создавать платформенно-специфичные реализации в Kotlin Multiplatform проектах.

### 📱 Примеры использования

#### Database Factory

```kotlin
// commonMain
expect class DatabaseFactory {
    fun create(): PressureResultsDatabase
}

// androidMain  
actual class DatabaseFactory(private val context: Context) {
    actual fun create(): PressureResultsDatabase {
        return Room.databaseBuilder(context, PressureResultsDatabase::class.java, "pressure.db").build()
    }
}

// iosMain
actual class DatabaseFactory {
    actual fun create(): PressureResultsDatabase {
        // iOS specific implementation
    }
}
```

#### Settings Store

```kotlin
// commonMain
expect class SettingsStore<T> {
    suspend fun save(value: T)
    fun get(): Flow<T>
}

// androidMain - DataStore
// iosMain - UserDefaults  
// desktopMain - File system
```

## 🎯 Рекомендации по использованию

### ✅ Лучшие практики

1. **🔍 Регулярно проверяйте LeakCanary** - запускайте debug сборки часто
2. **📊 Мониторьте Detekt отчеты** - исправляйте предупреждения своевременно
3. **⚡ Используйте параллельную сборку** - экономьте время разработки
4. **🔄 Обновляйте инструменты** - следите за новыми версиями
5. **📖 Изучайте отчеты** - понимайте что показывают инструменты

### ⚠️ Важные моменты

- **LeakCanary** работает только в debug сборках
- **Detekt** может быть настроен на автоисправление
- **KSP** требует чистой пересборки при изменении схемы
- **Version Catalogs** должен быть синхронизирован между модулями

## 🚀 Интеграция в рабочий процесс

### 📋 Ежедневная разработка

```bash
# 1. Обновление зависимостей
./gradlew --refresh-dependencies

# 2. Статический анализ
./gradlew detekt

# 3. Сборка debug версии с LeakCanary
./gradlew :composeApp:assembleDebug

# 4. Тестирование
./gradlew test

# 5. Установка и проверка
./gradlew :composeApp:installDebug
```

### 🔄 Перед коммитом

```bash
# Полная проверка качества кода
./gradlew clean detekt test build
```

### 📦 Релиз

```bash
# Сборка production версии (без LeakCanary)
./gradlew :composeApp:assembleRelease
```

---

Эти инструменты обеспечивают высокое качество кода, раннее обнаружение проблем и эффективную разработку BikeCalc Pro. Регулярное использование всех инструментов гарантирует стабильность и производительность приложения на всех поддерживаемых платформах.
