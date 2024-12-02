create table posts
(
    id          serial primary key,
    title       varchar not null,
    description varchar,
    created     timestamp,
    user_id     bigint references users (id)
);
