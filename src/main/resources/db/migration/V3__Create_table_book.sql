CREATE TABLE book_api.book (
	id serial NOT NULL,
	title varchar NULL,
	genre varchar NULL,
	release_year int4 NULL,
	author_id int4 NULL,
	CONSTRAINT book_pk PRIMARY KEY (id),
	CONSTRAINT book_author_fk FOREIGN KEY (author_id) REFERENCES public.author(id)
);