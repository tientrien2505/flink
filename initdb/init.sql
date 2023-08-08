create database database_source;
create database database_sink;

\c database_source;

create table if not exists transaction (
                                           id serial primary key,
                                           customer varchar (50),
    tran_at timestamp,
    tran_type varchar (10),
    amount float
    );

insert into transaction (
    customer,
    tran_at,
    tran_type,
    amount
) values (
             'abc123',
             '2023-05-25 10:00:00',
             'IN',
             100000
         ),
         (
             'abc123',
             '2023-05-25 11:00:00',
             'IN',
             100000
         ),
         (
             'abc123',
             '2023-05-24 11:00:00',
             'IN',
             100000
         )
;

\c database_sink

create table if not exists customer_daily (
    customer varchar(50),
    report_date date,
    amount_in float,
    amount_out float,
    total_amount_in float,
    total_amount_out float
    );