
create table if not exists manager
(
    id         bigserial
        primary key,
    address    varchar(255),
    first_name varchar(100),
    last_name  varchar(100),
    phone      integer
);

alter table manager
    owner to postgres;

create table if not exists client
(
    id         bigserial
        primary key,
    address    varchar(255),
    email      varchar(255),
    first_name varchar(100),
    last_name  varchar(100),
    phone      varchar(255),
    manager_id bigint
        constraint fkgg6rklgguqfpw6r7uc628l6lm
            references manager
);

alter table client
    owner to postgres;

create table if not exists credit_product
(
    id                   bigserial
        primary key,
    amount               numeric(19, 2),
    type                 integer,
    currency             varchar(255),
    current_account      bigint
        constraint uk_apanvd1nxvsht0xoq4fa3d0u6
            unique,
    insurance            varchar(255),
    last_paid_at         date,
    loan_balance         numeric(19, 2),
    loan_period_in_month integer
        constraint credit_product_loan_period_in_month_check
            check ((loan_period_in_month >= 2) AND (loan_period_in_month <= 300)),
    rate                 numeric(19, 2),
    client_id            bigint
        constraint fkn1t1e9tw7v3q1j9860mf4nfm1
            references client
);

alter table credit_product
    owner to postgres;

create table if not exists debet_account
(
    id              bigserial
        primary key,
    amount          numeric(19, 2),
    currency        varchar(255),
    current_account bigint,
    rate            numeric(19, 2),
    client_id       bigint
        constraint fk1mpjqqe5xpavqyw81t3ha7dkl
            references client
);

alter table debet_account
    owner to postgres;

create table if not exists card
(
    id               bigserial
        primary key,
    cc_number        bigint,
    payment_system   varchar(255),
    debet_account_id bigint
        constraint fkev4hdwsohjkreswogwia5rjbu
            references debet_account
);

alter table card
    owner to postgres;







insert into client (address, email, first_name, last_name, phone)
values ('Москва', 'example1@mail.ru', 'Иван', 'Иванов', '89093332211');

insert into client (address, email, first_name, last_name, phone)
values ('Пенза', 'example2@mail.ru', 'Петр', 'Петров', '89093332244');

insert into client (address, email, first_name, last_name, phone)
values ('Петербург', 'example3@mail.ru', 'Сидор', 'Сидоров', '89093332255');

insert into credit_product (amount, type, currency, current_account, insurance,
                            loan_period_in_month, rate, client_id)
values (1000000, 1,'USD', 1000001, 'ВСК', 60, 10, 1);

insert into credit_product (amount, type, currency, current_account, insurance,
                            loan_period_in_month, rate, client_id)
values (2000000, 2, 'EUR', 1000002, 'Ингосстрах', 120, 8, 2);

insert into credit_product (amount, type, currency, current_account, insurance,
                            loan_period_in_month, rate, client_id)
values (500000, 1, 'RUB', 1000003, 'Ресо', 240, 9, 3);

insert into debet_account (amount, currency, current_account, rate, client_id)
values (100000, 'RUB', 2000001, 5, 1);

insert into debet_account (amount, currency, current_account, rate, client_id)
values (200000, 'USD', 2000002, 6, 2);

insert into debet_account (amount, currency, current_account, rate, client_id)
values (100000, 'EUR', 2000003, 5, 3);

insert into card (cc_number, payment_system, debet_account_id)
values (58042356, 'VISA', 1);

insert into card (cc_number, payment_system, debet_account_id)
values (58042357, 'MasterCard', 2);

insert into card (cc_number, payment_system, debet_account_id)
values (58042357, 'VISA', 3);

