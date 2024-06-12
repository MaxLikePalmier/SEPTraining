create table author (id integer not null, name varchar(255), primary key (id)) engine=InnoDB;
create table author_seq (next_val bigint) engine=InnoDB;
insert into author_seq values ( 1 );
create table book (id integer not null, title varchar(255), primary key (id)) engine=InnoDB;
create table book_author (book_id integer not null, author_id integer not null, primary key (book_id, author_id)) engine=InnoDB;
create table book_seq (next_val bigint) engine=InnoDB;
insert into book_seq values ( 1 );
alter table book_author add constraint FKbjqhp85wjv8vpr0beygh6jsgo foreign key (author_id) references author (id);
alter table book_author add constraint FKhwgu59n9o80xv75plf9ggj7xn foreign key (book_id) references book (id);