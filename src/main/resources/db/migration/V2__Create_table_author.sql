CREATE TABLE IF NOT EXISTS book_api.author (
	id serial NOT NULL,
	name varchar NOT NULL,
	CONSTRAINT author_pk PRIMARY KEY (id)
);