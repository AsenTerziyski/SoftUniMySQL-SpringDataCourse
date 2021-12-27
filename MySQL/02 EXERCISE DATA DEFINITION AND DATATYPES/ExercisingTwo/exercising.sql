create database `minions`;
use minions;

create table `minions` (
id int primary key auto_increment,
name varchar(30) not null,
age int
);

create table `towns` (
town_id int primary key auto_increment,
town_name varchar(30) not null
);

alter table towns
change town_id id int auto_increment;

alter table minions
add column town_id int not null;

insert into minions (`name`,age,town_id)
values
('minion1', 10,1),
('minion2', 20,3),
('minion3', 30,2);

insert into towns (town_name)
values
('town1'),
('town2'),
('town3');

truncate minions;

drop table minions;
drop table towns;


