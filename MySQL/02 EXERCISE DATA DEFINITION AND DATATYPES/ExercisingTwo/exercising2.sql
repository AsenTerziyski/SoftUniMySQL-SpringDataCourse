
create table people(
id int primary key auto_increment unique,
name varchar(200) not null default 'name',
picture blob,
height float(5,2),
weight float(6,2),
gender char default 'm',
birthdate datetime, 
biography text

);

drop table people;

create table people(
id int primary key auto_increment unique,
name varchar(200) not null default 'name',
picture blob,
height float(5,2),
weight float(6,2),
gender char default 'm',
birthdate datetime, 
biography text
);

insert into people (name, picture, height, weight, birthdate, biography)
values
('name1','p1',1.25,50.50, '2001-05-05', 'xxxxxxxxaaaaaaaaaaaaa'),
('name2','p1',1.25,50.50, '2001-05-05', 'xxxxxxxxaaaaaaaaaaaaa'),
('name3','p1',1.25,50.50, '2001-05-05', 'xxxxxxxxaaaaaaaaaaaaa');

update  people
set gender = 'f'
where id = 2;

create table users (
id int primary key auto_increment,
username varchar(30) not null unique,
password varchar(26) not null unique,
profile_picture blob default 'picture',
last_login_time timestamp default current_timestamp,
is_deleted bool
);

drop table users;

create table users (
id int primary key auto_increment,
username varchar(30) not null unique,
password varchar(26) not null unique,
profile_picture blob,
last_login_time timestamp default current_timestamp,
is_deleted bool
);

insert into users (username, password, is_deleted)
values
('a', 'a123', true),
('b', 'a1x3', false),
('c', 'a1xx3', true);

alter table users
drop primary key,
add constraint pk_users
primary key (id, username);

alter table users
drop primary key,
add constraint pk_users_ver3
primary key (id,username,password);

alter table users
drop primary key,
add constraint pk_users
primary key (id, username);

alter table users
change last_login_time last_login_time timestamp default current_timestamp;

alter table users
change username username_unique varchar(100) not null unique;

