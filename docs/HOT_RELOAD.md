# 🔥 Compose Hot Reload

**Compose Hot Reload** — встроенный инструмент Compose Multiplatform для мгновенного обновления UI без перезапуска приложения. Позволяет видеть изменения в интерфейсе сразу после сохранения файла.

## ✅ Встроенная поддержка

Начиная с **Compose Multiplatform 1.10.0**, Hot Reload входит в состав платформы. Отдельный плагин подключать не нужно — достаточно настроенного desktop-таргета.

## 🚀 Запуск

```bash
# Запуск desktop-приложения с Hot Reload
./gradlew :composeApp:hotRunDesktop
```

**Альтернатива через IDE:** откройте `main.kt`, нажмите Run → выберите **Run with Compose Hot Reload**.

## 📋 Как использовать

1. **Запустите** приложение командой выше
2. **Измените** код в любом UI-файле (например, в `commonMain`)
3. **Сохраните** файл (Ctrl+S / Cmd+S)
4. **UI обновится** автоматически без перезапуска

## ✅ Поддерживается

- UI-компоненты Compose
- Тексты, стили, цвета, размеры
- Логика ViewModels
- Состояния приложения
- Навигация
- Темы

## ❌ Требует перезапуска

- Изменения в `main()`
- Добавление новых зависимостей
- Изменения в Gradle-файлах

## 📋 Требования

- **Kotlin 2.1.20+**
- **Compose Multiplatform 1.10.0+**
- **Desktop target** (jvm)
- **JetBrains Runtime** (JBR) — Gradle может автоматически подтянуть через Foojay resolver

## 🔗 Дополнительно

- [Официальная документация Kotlin](https://kotlinlang.org/docs/multiplatform/compose-hot-reload.html)
- [Compose Hot Reload на GitHub](https://github.com/JetBrains/compose-hot-reload)
