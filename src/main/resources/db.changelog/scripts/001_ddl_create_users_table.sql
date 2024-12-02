create table users
(
    id       serial primary key,
    name     varchar unique not null,
    email    varchar unique not null,
    password varchar        not null
);
