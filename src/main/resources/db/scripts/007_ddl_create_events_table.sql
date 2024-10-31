create table events
(
    id      serial primary key,
    created timestamp,
    user_id bigint references users (id),
    post_id bigint references posts (id)
);
