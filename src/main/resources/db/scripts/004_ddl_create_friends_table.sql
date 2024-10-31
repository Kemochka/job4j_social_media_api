create table friends
(
    id            serial primary key,
    status        boolean,
    user_offer_id bigint references users (id),
    user_accept   bigint references users (id)
);
