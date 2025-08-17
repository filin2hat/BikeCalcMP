# 📊 Kotzilla - Мониторинг производительности

## 🎯 Обзор

**Kotzilla** - это встроенная система мониторинга производительности для Kotlin приложений,
использующих Koin. Она предоставляет глубокую видимость на уровне Koin для выявления и решения
проблем как во время разработки, так и в продакшене.

### 🌟 Ключевые возможности

- **🔍 Визуализация графа зависимостей** в Android Studio/IntelliJ
- **🐛 Отладка и профилирование** во время разработки
- **📱 Мониторинг производительности** на всех устройствах в продакшене
- **🚀 Автоматическое отслеживание** без ручной инструментации
- **📊 Поддержка Android, KMP и SDK библиотек**

## 🛠️ Настройка в проекте

### 1. Конфигурация

Файл конфигурации: `composeApp/kotzilla.json`

```json
{
  "sdkVersion": "1.1.0",
  "keys": [
    {
      "appId": "f051e7d4-926e-4874-bgg41-4261a714a482",
      "applicationPackageName": "dev.filinhat.bikecalc",
      "keyId": "01973923-afff-7cc7-8ca2-c40gg86dcbcaa1",
      "apiKey": "ktz-sdk-KyPJN8Tpu8_6DYYqggwqqENDn23QqAcfamaPNiPkCj5XU",
      "isDefault": true
    }
  ]
}
```

### 2. Зависимости

В `gradle/libs.versions.toml`:

```toml
[versions]
kotzilla = "1.1.0"

[libraries]
kotzilla-sdk-ktor3 = { group = "io.kotzilla", name = "kotzilla-sdk-ktor3", version.ref = "kotzilla" }

[plugins]
kotzilla = { id = "io.kotzilla.kotzilla-plugin", version.ref = "kotzilla" }
```

В `composeApp/build.gradle.kts`:

```kotlin
plugins {
    alias(libs.plugins.kotzilla)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotzilla.sdk.ktor3)
        }
    }
}
```

### 3. Инициализация

В `composeApp/src/commonMain/kotlin/dev/filinhat/bikecalc/di/InitKoin.kt`:

```kotlin
import io.kotzilla.sdk.analytics.koin.analytics

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(sharedModule, platformModule)
        try {
            analytics()
            println("✅ Kotzilla analytics успешно инициализирован")
        } catch (e: NotImplementedError) {
            println("⚠️ Analytics not supported on this platform: ${e.message}")
        } catch (e: Exception) {
            println("❌ Ошибка инициализации Kotzilla analytics: ${e.message}")
            e.printStackTrace()
        }
    }
}
```

## 🔧 Автоматическая генерация ключей

### Генерация ключа

Kotzilla автоматически генерирует ключ в `composeApp/src/main/assets/kotzilla.key`:

```
4396e7a24698780e7cb2afac5jjlk2a0c6187cbd3:9OQnVLwtsJpfm8AKRQpcwc1JOZhDri+ZIK86t63Gpyoz2
```

### Очистка и пересборка

```bash
# Очистка старого ключа
./gradlew clean

# Генерация нового ключа
./gradlew :composeApp:assembleDebug
```

## 📊 Что отслеживается

### 🔍 Во время разработки

- **Визуализация графа зависимостей** Koin
- **Отслеживание времени запуска** приложения
- **Профилирование разрешения компонентов**
- **Выявление медленных или блокирующих операций**
- **Анализ потоков выполнения**

### 📱 В продакшене

- **Мониторинг стабильности** на реальных устройствах
- **Отслеживание производительности** на разных версиях ОС
- **Анализ пользовательских сессий**
- **Выявление проблем**, проявляющихся только в продакшене
- **Мониторинг SDK библиотек** в изолированных контейнерах

## 🚨 Устранение неполадок

### Проблема: "Skipping Kotzilla key generation"

```
Task :composeApp:generateAndroidAssetsKey Skipping Kotzilla key generation from: PROJECT_FILE - key already exists for 'dev.filinhat.bikecalc'
```

**Решение:**

1. Очистите кэш: `./gradlew clean`
2. Пересоберите проект: `./gradlew :composeApp:assembleDebug`
3. Проверьте, что файл `kotzilla.key` создался в `composeApp/src/main/assets/`

### Проблема: Analytics не инициализируется

```
❌ Ошибка инициализации Kotzilla analytics: [error message]
```

**Решение:**

1. Проверьте конфигурацию в `kotzilla.json`
2. Убедитесь, что зависимости подключены правильно
3. Проверьте права доступа к файлу ключа
4. Посмотрите полный стек ошибки в логах

### Проблема: Статистика не собирается

**Решение:**

1. Проверьте, что приложение запускается на поддерживаемой платформе
2. Убедитесь, что Koin инициализируется правильно
3. Проверьте логи на наличие ошибок инициализации
4. Убедитесь, что файл ключа присутствует в assets

## 📈 Интеграция с CI/CD

### GitHub Actions

Kotzilla автоматически работает в CI/CD pipeline:

- **Генерация ключей** происходит автоматически при сборке
- **Тестирование** включает проверку инициализации Kotzilla
- **Артефакты** содержат отчеты о производительности

### Локальная разработка

```bash
# Сборка с Kotzilla
./gradlew :composeApp:assembleDebug

# Проверка ключа
ls -la composeApp/src/main/assets/kotzilla.key

# Запуск с отладкой
./gradlew :composeApp:installDebug
```

## 🔗 Полезные ссылки

- **[Официальный сайт](https://kotzilla.io/)** - Документация и примеры
- **[Документация](https://doc.kotzilla.io/)** - Подробное руководство
- **[Koin Framework](https://insert-koin.io/)** - Dependency Injection
- **[GitHub Repository](https://github.com/ekito/koin)** - Исходный код Koin

## 📋 Чек-лист настройки

- [ ] Конфигурация `kotzilla.json` создана
- [ ] Зависимости добавлены в `libs.versions.toml`
- [ ] Плагин подключен в `build.gradle.kts`
- [ ] Инициализация добавлена в `InitKoin.kt`
- [ ] Ключ сгенерирован в `assets/kotzilla.key`
- [ ] Приложение запускается без ошибок
- [ ] Логи показывают успешную инициализацию
- [ ] Статистика собирается в продакшене

## 🎯 Лучшие практики

1. **🔍 Регулярно проверяйте логи** инициализации
2. **📊 Мониторьте производительность** в продакшене
3. **🛠️ Используйте визуализацию** графа зависимостей
4. **🚀 Оптимизируйте время запуска** приложения
5. **📱 Тестируйте на разных платформах** и устройствах
6. **🔄 Обновляйте SDK** до последних версий
7. **📈 Анализируйте метрики** производительности
