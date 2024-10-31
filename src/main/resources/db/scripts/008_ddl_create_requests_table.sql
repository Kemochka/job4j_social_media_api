create table requests
(
    id           serial primary key,
    created      timestamp,
    user_from_id bigint references users (id),
    user_to_id   bigint references users (id)
);
