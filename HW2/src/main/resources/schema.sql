create table if not exists author (id integer not null, name varchar(255), primary key (id)) engine=InnoDB;
create table if not exists author_seq (next_val bigint) engine=InnoDB;
-- insert into author_seq values ( 1 );
INSERT INTO author_seq (next_val)
    SELECT 1
    WHERE NOT EXISTS (SELECT 1 FROM author_seq LIMIT 1);
create table if not exists book (id integer not null, title varchar(255), primary key (id)) engine=InnoDB;
create table if not exists book_author (book_id integer not null, author_id integer not null, primary key (book_id, author_id)) engine=InnoDB;
create table if not exists book_seq (next_val bigint) engine=InnoDB;
-- insert into book_seq values ( 1 );
INSERT INTO book_seq (next_val)
    SELECT 1
    WHERE NOT EXISTS (SELECT 1 FROM book_seq LIMIT 1);
alter table book_author add foreign key (author_id) references author (id);
alter table book_author add foreign key (book_id) references book (id);