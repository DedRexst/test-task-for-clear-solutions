CREATE TABLE users
(
    id UUID PRIMARY KEY,
    first_name varchar(30) not null,
    last_name varchar(30) not null,
    email varchar(50) UNIQUE not null,
    birth_date date not null,
    address varchar(100),
    phone_number varchar(15)
);