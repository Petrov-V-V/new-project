insert into products_petrov_vv.roles (name)
values ('ROLE_USER');
insert into products_petrov_vv.roles (name)
values ('ROLE_ADMIN');

INSERT INTO products_petrov_vv.user_roles(
	role_id, user_id)
	VALUES (2, 1);