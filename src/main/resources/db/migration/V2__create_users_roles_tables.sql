create table if not exists bot.users (
	id serial primary key,
	username varchar(100) not null,
	password varchar(100) not null
);

create table if not exists bot.roles (
	id serial primary key,
	name varchar(100) not null
);

create table if not exists bot.users_roles (
	user_id bigint,
	role_id bigint,
	primary key (user_id, role_id),
	foreign key(user_id) references bot.users(id),
	foreign key(role_id) references bot.roles(id)
);

insert into bot.roles(name) 
values
	('ROLE_ADMIN'),
	('ROLE_USER');
	
insert into bot.users (username, password) 
values
	('admin', '$2a$12$0BiF6FGJY6j3xKhj3h5BMuYnD..row1CaYu3n7.apng4j4z38uacC');

insert into bot.users_roles (user_id, role_id) 
values 
	(1, 1);