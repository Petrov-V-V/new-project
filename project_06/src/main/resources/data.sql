create schema if not exists petrov;

create table petrov.product
(
    id    integer generated always as identity
        primary key,
    name  varchar(255) not null,
    price numeric      not null
);

create table petrov.cart
(
    id        integer generated always as identity
        primary key,
    promocode varchar(255)
);

create table petrov.client
(
    id       integer generated always as identity,
    name     varchar(255) not null,
    username varchar(255) not null,
    password varchar(255) not null,
    cart_id  integer      not null
        constraint client_cart_id_fk
            references petrov.cart,
    email varchar(255) not null
);

create table petrov.product_client
(
    id         integer generated always as identity
        primary key,
    id_product integer not null
        constraint product_client_products_id_fk
            references petrov.product,
    id_cart    integer not null
        constraint product_client_cart_id_fk
            references petrov.cart,
    count      integer not null
);