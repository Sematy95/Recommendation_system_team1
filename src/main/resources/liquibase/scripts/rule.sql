-- liquibase formatted sql

-- changeset dmitri:1
create sequence IF NOT EXISTS arguments_seq start with 1 increment by 1;
create sequence IF NOT EXISTS condition_seq start with 1 increment by 1;
create sequence IF NOT EXISTS dynamic_rule_seq start with 1 increment by 1;
CREATE TABLE IF NOT EXISTS dynamic_rule
(
    id
    BIGINT
    PRIMARY
    KEY
    NOT
    NULL,
    product_name
    TEXT
    NOT
    NULL,
    product_id
    UUID
    NOT
    NULL,
    product_text
    TEXT
    NOT
    NULL
);


CREATE TABLE IF NOT EXISTS condition
(
    id
    BIGINT
    PRIMARY
    KEY
    NOT
    NULL,
    query
    TEXT
    NOT
    NULL,
    product_Type
    TEXT
    NOT
    NULL,
    transaction_Name
    TEXT,
    compare_Type
    TEXT,
    compare_Value
    BIGINT,
    negate
    BOOLEAN
    NOT
    NULL
);


CREATE TABLE IF NOT EXISTS dynamic_rule_conditions
(
    dynamic_rule_id
    BIGINT
    NOT
    NULL,
    conditions_id
    BIGINT
    NOT
    NULL
);

-- changeset sematy:2
INSERT INTO dynamic_rule (id, product_name, product_id, product_text)
VALUES (1, 'Invest 500', '147f6a0f-3b91-413b-ab99-87f081d60d5a',
        'Откройте свой путь к успеху с индивидуальным инвестиционным счетом (ИИС) от нашего банка! Воспользуйтесь налоговыми льготами и начните инвестировать с умом. Пополните счет до конца года и получите выгоду в виде вычета на взнос в следующем налоговом периоде. Не упустите возможность разнообразить свой портфель, снизить риски и следить за актуальными рыночными тенденциями. Откройте ИИС сегодня и станьте ближе к финансовой независимости!'),
       (2, 'Top Saving', '59efc529-2fff-41af-baff-90ccd7402925', 'Откройте свою собственную «Копилку» с нашим банком! «Копилка» — это уникальный банковский инструмент, который поможет вам легко и удобно накапливать деньги на важные цели. Больше никаких забытых чеков и потерянных квитанций — всё под контролем!
Преимущества «Копилки»:
Накопление средств на конкретные цели. Установите лимит и срок накопления, и банк будет автоматически переводить определенную сумму на ваш счет.
Прозрачность и контроль. Отслеживайте свои доходы и расходы, контролируйте процесс накопления и корректируйте стратегию при необходимости.
Безопасность и надежность. Ваши средства находятся под защитой банка, а доступ к ним возможен только через мобильное приложение или интернет-банкинг.
Начните использовать «Копилку» уже сегодня и станьте ближе к своим финансовым целям!'),
       (3, 'Простой кредит', 'ab138afb-f3ba-4a93-b74f-0fcee86d447f', 'Откройте мир выгодных кредитов с нами!
Ищете способ быстро и без лишних хлопот получить нужную сумму? Тогда наш выгодный кредит — именно то, что вам нужно! Мы предлагаем низкие процентные ставки, гибкие условия и индивидуальный подход к каждому клиенту.
Почему выбирают нас:
Быстрое рассмотрение заявки. Мы ценим ваше время, поэтому процесс рассмотрения заявки занимает всего несколько часов.
Удобное оформление. Подать заявку на кредит можно онлайн на нашем сайте или в мобильном приложении.
Широкий выбор кредитных продуктов. Мы предлагаем кредиты на различные цели: покупку недвижимости, автомобиля, образование, лечение и многое другое.
Не упустите возможность воспользоваться выгодными условиями кредитования от нашей компании!');

-- changeset sematy:3
INSERT INTO condition (id, query, product_Type, transaction_Name, compare_Type, compare_Value, negate) VALUES
(1, 'USER_OF', 'INVEST', NULL, NULL, NULL, TRUE),
(2, 'TRANSACTION_SUM_COMPARE', 'SAVING', 'DEPOSIT', '>=', 1000, FALSE),
(3, 'USER_OF', 'DEBIT', NULL, NULL, NULL, FALSE),
(4, 'TRANSACTION_SUM_COMPARE', 'DEBIT', 'DEPOSIT', '>=', 50000, false),
(5, 'TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW', 'DEBIT', null, '>', null, false),
(6, 'USER_OF', 'CREDIT', null, null, null,true),
(7, 'TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW', 'DEBIT', null, '>', null, false),
(8, 'TRANSACTION_SUM_COMPARE', 'DEBIT', 'DEPOSIT', '>', 100000, false);

-- changeset sematy:4
INSERT INTO dynamic_rule_conditions (dynamic_rule_id, conditions_id) VALUES
(1,1),(1,2),(1,3),
(2,4),(2,5),(2,6),
(3,6),(3,7),(3,8);





