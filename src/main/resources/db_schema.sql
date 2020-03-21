create table users(
	id serial primary key,
	name varchar(255) not null,
	balance real not null
);

create table plans(
	id serial primary key,
	name varchar(255) not null,
	price real not null,
	duration_unit varchar(255) not null,
	duration_count integer not null
);

create table subscriptions(
	id serial primary key,
	user_id integer not null references users(id) on delete cascade,
	plan_id integer not null references plans(id) on delete cascade,
	start_time timestamp without time zone not null,
	expiration_time timestamp without time zone not null
);
