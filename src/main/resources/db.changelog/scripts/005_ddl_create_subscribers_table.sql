create table subscribers
(
    id                 serial primary key,
    user_subscriber_id bigint references users (id),
    user_to_id         bigint references users (id)
);
