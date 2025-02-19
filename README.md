# Рекомендательная система для улучшения предоставления маркетинговых услуг и помощь сотрудникам банка в предложении персонализированных услуг клиентам

Основные задачи системы:
* Персонализация предложений: Система анализирует данные о клиентах (типы продуктов, транзакции, их количество и сумму) для формирования индивидуальных рекомендаций по продуктам и услугам банка.
* Повышение эффективности маркетинга: Система помогает оптимизировать маркетинговые кампании, предлагая наиболее релевантные продукты целевым группам клиентов, не только используя веб запросы ,но также с доступом через мессенджер Telegram. 
* Поддержка сотрудников банка: Система предоставляет сотрудникам доступ к информации о клиентах и рекомендациям, помогая им предлагать клиентам наиболее подходящие продукты и услуги, с учётом динамических изменений политики банка в области предложений клиентам.

## Технологический стек проекта:

Данный технологический стек обеспечивает надежную и эффективную работу приложения, а также удобство разработки и поддержки.

### Язык программирования и фреймворк для создания серверной части приложения:
* Java 17: Современная версия языка Java, обеспечивающая высокую производительность и безопасность.
* Spring Framework: Мощный и гибкий фреймворк, предоставляющий широкие возможности для разработки веб-приложений и микросервисов.
### Документация и тестирование:
* Swagger: Инструмент для автоматического создания и документирования REST API, что упрощает разработку и тестирование.
* JavaDoc: Система для генерации документации на основе комментариев в коде Java, обеспечивающая актуальность и понятность кода.
### Работа с базами данных:
* Liquibase: Инструмент для управления миграциями базы данных, позволяющий легко и безопасно обновлять структуру базы данных при изменениях в проекте.
* PostgreSQL: Надежная и масштабируемая реляционная база данных, используемая для хранения данных, необходимых для общения с клиентом через Telegram-бот.
* H2 (JDBC): Легковесная встраиваемая база данных, используемая для хранения тестовых данных, а также данных о пользователях ("USERS"), продуктах банка ("PRODUCTS") и пользовательских транзакциях ("TRANSACTIONS").
### Интеграция с Telegram:
com.github.pengrad: Библиотека Java, обеспечивающая удобную интеграцию с Telegram API для создания и управления Telegram-ботами.

# Развёртывание приложения
1. Установка и настройка СУБД PostgreSQL
* Создание пользователя:

```$ sudo -u postgres createuser <username>```

* Создание базы данных:

```$ sudo -u postgres createdb <dbname>```

* Создание пароля для пользователя:

```$ sudo -u postgres psql```

```psql=# alter user <username> with encrypted password '<password>';```

* Предоставление привилегий пользователя к базе данных:

```psql=# grant all privileges on database <dbname> to <username> ;```

2. Клонировать репозиторий

* С помощью IntelliJ IDEA (Project from VCS)

* С помощью консоли:

`git clone https://github.com/Sematy95/Recommendation_system_team1`

3. Установить зависимости

* Через IntelliJ IDEA и перейте Maven -> Sync 

* Через консоль:

```sh```

```mvn clean install```

4. Изменить параметры подключения к базе данных и токен telegram bot (application.properties)

```spring.datasource.url= jdbc:postgresql://[host]:[port]/[db_name]```

```spring.datasource.username=[db_user]```

```spring.datasource.password=[db_password]```

```telegram.bot.token=[telegramBot_token]```

5. Запустить приложение

* Через Intellij IDEA: run

* Через консоль:

```sh```

```mvn spring-boot:run```

# Подробности
Для просмотра документации откройте wiki
