create database instd;
use instd;

#SECTION 01

#problem01
create table users (
	id int primary key auto_increment,
    username varchar(30) not null unique,
	password varchar(30) not null,
	email varchar(50) not null,
	gender char(1) not null,
	age int not null,
	job_title varchar(40) not null,
	ip varchar(30) not null
);

create table addresses (
	id int primary key auto_increment,
	address varchar(30) not null,
	town varchar(30) not null,
	country varchar(30) not null,
	user_id int not null,

    constraint fk_addresses_users
    foreign key (user_id) references users(id)
);

create table photos (
	id int primary key auto_increment,
	description text not null,
	date datetime not null,
	views int not null default 0
);

create table comments (
	id int primary key auto_increment,
	comment varchar(255) not null,
	date datetime not null,
	photo_id int not null,
    
    constraint fk_comments_photos
    foreign key (photo_id) references photos(id)
);

create table users_photos (
user_id int not null,
photo_id int not null,

constraint fk_users_photos_users
foreign key (user_id) references users(id),

constraint fk_users_photos_photos
foreign key (photo_id) references photos(id)
);

create table likes (
	id int primary key auto_increment,
	photo_id int,
	user_id int,
    
constraint fk_likes_photos
foreign key (photo_id) references photos(id),

constraint fk_likes_users
foreign key (user_id) references users(id)
);

-- #SECTION 02

-- #problem02
-- insert into addresses (address, town, country, user_id)
-- select u.username, u.password, u.ip, u.age  from users as u
-- where u.gender = 'M';

-- #problem 03
-- update addresses
-- set country = (
-- case
-- 	when left(country,1) = 'B' then 'Blocked'
-- 	when left(country,1) = 'T' then 'Test'
-- 	when left(country,1) = 'P' then 'In Progress'
-- end)
-- # да не забравям where клаузата:
-- where left(country,1) in ('B', 'T', 'P');


-- #problem04
-- delete from addresses as a
-- where a.id % 3 = 0;

#SECTION 03

#problem05
select u.username, u.gender, u.age from users as u
order by u.age desc, u.username asc;

#problem06
select p.id, p.date as date_and_time, p.description, count(c.comment) as commentsCount from photos as p
join comments as c
on c.photo_id = p.id
group by p.id
order by commentsCount desc, p.id asc
limit 5;

#problem07
select concat_ws('.', u.id, u.username) as id_username, u.email from users as u
join users_photos as up
on up.photo_id = u.id
join photos as p
on p.id = up.photo_id
where up.photo_id = up.user_id
order by u.id asc;

#problem08
#variant 1
select p.id as photo_id, count(distinct l.id) as likes_count,count(distinct c.comment) as comments_count
from photos as p
left join likes as l
on l.photo_id = p.id
left join comments as c
on c.photo_id = p.id
group by p.id
order by likes_count desc, comments_count desc,p.id asc;

#variant 2
select p.id,
(select count(*) from likes as l where l.photo_id = p.id) as likes_count,
(select count(*) from comments as c where c.photo_id = p.id) as comments_count
from photos as p
group by p.id
order by likes_count desc, comments_count desc,p.id asc;

#problem09
select concat(substring(description, 1, 30),'...') as summary, date from photos as p
where day(p.date ) = 10
order by date desc;

# вариант с лефт:
select concat(left(description,30),'...') as summary, date from photos as p
where day(p.date ) = 10
order by date desc;

#SECTION 04 Programmability

# 01 create functions 
-- delimiter $$
-- create function udf_name (......)
-- returns int
-- deterministic
-- begin
-- end$$
-- delimiter ;

-- # 02 create procedures
-- delimiter $$
-- create procedure udp_.....(........)
-- begin
-- end$$
-- delimiter ;

#problem10

#first:
-- select count(up.user_id) from users as u
-- left join users_photos as up
-- on u.id = up.user_id
-- where u.username = 'ssantryd'
-- group by u.username;

delimiter $$
create function udf_users_photos_count (fusername VARCHAR(30))
returns int
deterministic
begin
return (
		select count(up.user_id) from users as u
		left join users_photos as up
		on u.id = up.user_id
		where u.username = fusername	
		group by u.username
);
end$$
delimiter ;

SELECT udf_users_photos_count('djura2') AS photosCount;


#problem 11
-- update users as u
-- join addresses as a
-- on a.user_id = u.id
-- set u.age = u.age + 10
-- where a.town = '' and a.address = '' and a.user_id is not null;

delimiter $$
create procedure udp_modify_user (p_address VARCHAR(30), p_town VARCHAR(30))
begin

update users as u
join addresses as a
on a.user_id = u.id
set u.age = u.age + 10
where a.town = p_town and a.address = p_address and a.user_id is not null;

end$$
delimiter ;

CALL udp_modify_user ('97 Valley Edge Parkway', 'Divinópolis');
SELECT u.username, u.email,u.gender,u.age,u.job_title FROM users AS u
WHERE u.username = 'eblagden21';

























