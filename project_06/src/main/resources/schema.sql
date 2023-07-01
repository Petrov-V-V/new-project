create table products_petrov_vv.carts (id serial not null, promocode varchar(255), primary key (id));
create table products_petrov_vv.clients (cart_id integer not null, id serial not null, email varchar(255), name varchar(255) not null, password varchar(255) not null, username varchar(255) not null, primary key (id));
create table products_petrov_vv.products (count integer not null, id serial not null, price numeric(38,2) not null, name varchar(255) not null, primary key (id));
create table products_petrov_vv.products_carts (count integer not null, id serial not null, id_cart integer not null, id_product integer not null, primary key (id));
alter table if exists products_petrov_vv.clients add constraint FKf6u6267ugv1y03prbphkc7vs3 foreign key (cart_id) references products_petrov_vv.carts;
alter table if exists products_petrov_vv.products_carts add constraint FK58gy0a22e614p332h9d1b2wsg foreign key (id_cart) references products_petrov_vv.carts;
alter table if exists products_petrov_vv.products_carts add constraint FK5xn82l6dcw7ltw5q14me13yuk foreign key (id_product) references products_petrov_vv.products;
