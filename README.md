# Avtomator Govli

Модульное Android-приложение для автоматизации розничной торговли. Проект построен на Jetpack Compose, Clean Architecture и поддерживает офлайн-режим с локальной базой Room и готовностью к синхронизации.

## Ключевые возможности
- Локальная аутентификация с ролями (администратор, менеджер, кассир).
- Кассовый экран (POS) с быстрым поиском товаров и расчетом итогов.
- Управление товарами, покупателями, поставщиками и закупками.
- Отчеты по продажам, остаткам и активности кассиров.
- Настройки магазина и программа лояльности.
- Поддержка DataStore, Room, Hilt, Navigation Compose, WorkManager (модуль sync).

## Структура модулей
- `app` – точка входа и навигация.
- `core` – доменная и дата-логика, база данных, дизайн-система.
- `feature-*` – независимые экраны (auth, pos, products, inventory, purchases, customers, suppliers, reports, settings).
- `sync` – заглушка для фоновой синхронизации.

## Сборка и тесты
```bash
./gradlew assembleDebug
./gradlew :core:test
```

> ⚙️ **Gradle Wrapper без бинарников**
>
> В репозитории отсутствует бинарный `gradle-wrapper.jar`, чтобы pull-request можно было создавать без ошибки «Бинарные файлы не поддерживаются».
> Скрипты `gradlew` и `gradlew.bat` автоматически восстанавливают JAR из текстового `gradle-wrapper.jar.base64` при первом запуске.
> Если автоматическая сборка не сработала, выполните вручную:
>
> ```bash
> base64 --decode gradle/wrapper/gradle-wrapper.jar.base64 > gradle/wrapper/gradle-wrapper.jar
> ```
>
> или на Windows используйте `certutil -decode`.
