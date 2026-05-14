# Stellar Burgers UI Tests

UI autotests for `https://stellarburgers.education-services.ru/`.

## Stack

- Java 11
- JUnit 4
- Selenium WebDriver
- WebDriverManager
- Allure
- Page Object Model

## Run

Chrome:

```bash
mvn clean test -Dbrowser=chrome
```

Chrome with a custom binary path:

```bash
mvn clean test -Dbrowser=chrome -Dchrome.binary=/path/to/google-chrome
```

Yandex Browser:

```bash
mvn clean test -Dbrowser=yandex -Dyandex.binary=/path/to/yandex-browser
```

Headless mode:

```bash
mvn clean test -Dbrowser=chrome -Dheadless=true
```

Allure report:

```bash
mvn allure:report
```
