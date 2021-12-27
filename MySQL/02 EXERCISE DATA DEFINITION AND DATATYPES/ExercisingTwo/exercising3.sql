drop schema minions;
create schema my_soft_uni;
use my_soft_uni;

create table towns(
id int primary key auto_increment,
name varchar(30) not null 
);

insert into towns (name)
values
('town1'),
('town2'),
('town3');


