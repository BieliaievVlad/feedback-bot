create schema if not exists bot;

create table if not exists bot.feedback (
	id serial primary key,
	created_at timestamp default current_timestamp,
	chat_id bigint,
	position varchar(100),
	branch varchar(255),
	feedback_text text,
	category varchar(25),
	level integer,
	solution text
);