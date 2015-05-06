drop database if exists libreriadb;
create database libreriadb;

use libreriadb;

create table users (
	username	varchar(20) not null primary key,
	userpass	char(32) not null,
	name		varchar(70) not null
);

create table user_roles (
	username			varchar(20) not null,
	rolename 			varchar(20) not null,
	foreign key(username) references users(username) on delete cascade,
	primary key (username, rolename)
);

create table authors (
	name				varchar(80) not null primary key
);

create table books (
	bookid				int not null auto_increment primary key,
	title	 			varchar(100) not null,
	author	 			varchar(80) not null,
	language			varchar(100) not null,
	edition				varchar(100) not null,
	editionDate			varchar(100) not null,
	printingDate		varchar(100) not null,
	publisher			varchar(100) not null,
	foreign key(author)	references authors(name) on delete cascade
);

create table reviews (
	reviewid 			int not null auto_increment primary key,
	username 			varchar(20) not null,
	name				varchar(70) not null,
	book				int not null,
	content				varchar(500) not null,
	last_modified			timestamp default current_timestamp ON UPDATE CURRENT_TIMESTAMP,
	creation_timestamp		datetime not null default current_timestamp,
	foreign key(username) 	references users(username) on delete cascade,
	foreign key(book)		references books(bookid) on delete cascade
);