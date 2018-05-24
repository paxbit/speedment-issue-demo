create table cookie_types
(
	type varchar not null
		constraint cookies_pkey
			primary key,
	weight integer not null,
	diameter integer not null
)
;

create unique index cookies_id_uindex
	on cookie_types (type)
;

create table ingredients
(
	owner varchar not null
		constraint ingredients_pkey
			primary key
		constraint ingredients_cookie_types_type_fk
			references cookie_types
				on update cascade on delete cascade,
	name varchar not null
)
;

