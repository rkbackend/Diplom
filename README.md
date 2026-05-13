# Stellar Burgers API Tests

Автотесты для API сервиса Stellar Burgers.

## Технологии

| Инструмент | Версия |
| --- | --- |
| Java | 11 |
| Maven | 3.8+ |
| JUnit | 4.13.2 |
| REST Assured | 5.5.7 |
| Allure JUnit 4 | 2.34.0 |
| Lombok | 1.18.34 |

## Запуск

Проект тестирует публичный API `https://stellarburgers.education-services.ru/api`.

Запуск тестов:

```bash
mvn clean test
```

Генерация Allure-отчета:

```bash
mvn allure:report
```

Сгенерированный отчет сохраняется в директорию `allure-report`.
