# PROBLEMS
##########################################
# PROBLEM 01:
create table mountains (
id int primary key auto_increment,
`name` varchar(45) not null
);
create table peaks (
id int primary key auto_increment,
`name` varchar(45) not null,
mountain_id int,
constraint `fk_peaks_mountains`
foreign key (mountain_id)
references peaks(id)
);
#може и с алтер тейбъл:
-- alter table peaks
-- add constraint `fk_peaks_mountains`
-- foreign key (mountain_id)
-- references peaks(id);

-- drop table peaks;
-- drop table mountains;
##########################################

# PROBLEM 02:
select c.id as driver_id, v.vehicle_type, concat_ws(' ', c.first_name, c.last_name) as driver_name
from campers as c
join vehicles as v
on v.driver_id = c.id;

select driver_id, vehicle_type, concat_ws(' ', first_name, last_name) from vehicles as v
join campers as c
on v.driver_id = c.id;
##########################################

# PROBLEM 03:
SELECT 
    r.starting_point AS route_starting_point,
    r.end_point AS route_ending_point,
    r.leader_id,
    CONCAT_WS(' ', c.first_name, c.last_name) AS leader_name
FROM routes AS r
JOIN campers AS c 
ON r.leader_id = c.id;
##########################################

# PROBLEM 04:
drop table mountains;
drop table peaks;

create table mountains (
id int primary key auto_increment,
`name` varchar(45)
);

create table peaks (
id int primary key auto_increment,
`name` varchar(45) not null,
mountain_id int
-- constraint `fk_peaks_mountains`
-- foreign key (mountain_id)
-- references peaks(id)
-- -- on delete cascade
);

alter table peaks
add constraint `fk_peaks_mountains`
foreign key (mountain_id)
references mountains (id)
on delete cascade;

drop table mountains;

#най напред трябва да изтрия зависимата таблица:
drop table peaks;
drop table mountains;
##########################################

# PROBLEM 05:
create database project_management ;
use project_management ;

create table clients (
	id int(11) primary key auto_increment,
    client_name varchar(100)
);

create table projects (
	id int(11) primary key auto_increment,
    client_id int(11),
    project_lead_id int(11),
    constraint `fk_projects_clients`
    foreign key (client_id)
    references clients(id)
);

create table employees (
	id int primary key auto_increment,
    first_name varchar(30) not null,
    last_name varchar(30) not null,
    project_id int(11),
    constraint `fk_employees_projects`
    foreign key (project_id)
    references projects(id)
);

alter table projects
add constraint `fk_projects_employees`
foreign key (project_lead_id)
references employees(id);

#XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
#DEMOES
#demo01
create database employees_and_projects;
use employees_and_projects;

create table employees (
id int primary key auto_increment,
first_name varchar(50) not null,
last_name_name varchar(50) not null,
department varchar(50) not null
);

create table projects (
id int primary key auto_increment,
project_name varchar(100) not null
);

create table employee_project (
id int primary key auto_increment,
project_id int,
employee_id int,

constraint `fk_projects`
foreign key (project_id)
references projects(id),

constraint `fk_employees`
foreign key (employee_id)
references employees(id)
);

#demo02
select * from campers as c
join vehicles as v
on v.driver_id = c.id;

#demo03
select * from campers
join vehicles
on vehicles.driver_id = campers.id ;

#demo04
select campers.id from campers
join vehicles
on vehicles.driver_id = campers.id ;

#DEMOES FROM PRESENTATION
#PRESENT01
create database presentation_schema;
use presentation_schema;
create table mountains(
	id int primary key auto_increment,
    `name` varchar(50) not null
);
create table peaks (
id int primary key auto_increment,
`name` varchar(50) not null,
mountain_id int
);
alter table peaks
add constraint `fk_peaks_mountains`
foreign key (mountain_id)
references mountains(id);

#PRESENT02
create database presentation_schema2;
use presentation_schema2;
create table employees(
employee_id int primary key auto_increment,
employee_name varchar(50) 
);
create table projects(
project_id int primary key auto_increment,
proeject_name varchar(50)
);
create table employees_projects (
	employee_id int,
    project_id int,
    
    #правя компоситен примари кий:
    constraint employees_projects
    primary key (employee_id, project_id ),
    
    constraint `fk_employees_projects_employees`
    foreign key (employee_id) references employees (employee_id),
    
    constraint `fk_employees_projects_projects`
    foreign key (project_id) references projects(project_id)
    
);
drop schema presentation_schema2;
drop schema presentation_schema;

#PRESENT03
create database presentation_schema3;
use presentation_schema3;

create table drivers(
	driver_id int primary key auto_increment,
    driver_name varchar(50) not null
);

create table cars(
	car_id int primary key auto_increment,
    driver_id int,
    constraint fk_cars_drivers
    foreign key (driver_id) references drivers(driver_id)
);

#PRESENT04
create database presentation_schema4;
use presentation_schema4;

create table tableA (
id int primary key auto_increment,
common_column varchar(30)
);

create table tableB (
id int primary key auto_increment,
common_column varchar(30)
);

select * from tablea
join tableb on tableb.common_column = tablea.common_column;

drop schema presentation_schema4;

#PRESENT05
select driver_id, vehicle_type, concat_ws(' ', c.first_name, c.last_name) as driver_name from vehicles as v
join campers as c 
on v.driver_id = c.id
-- group by driver_id, vehicle_type
-- order by v.driver_id
;

#demo cascade
#CASCADE OPERATIONS can be either delete or update
create schema presentation_schema5;
use presentation_schema5;

create table montains(
id int primary key auto_increment,
mountain_name varchar(50) not null
);

create table peaks (
id int primary key auto_increment,
peak_name varchar(50) not null,
mountain_id int,

constraint fk_peaks_mountains
foreign key (mountain_id) references montains(id)
on delete cascade
);

delete from montains where mountain_name = 'Rila';
delete from peaks where id = 7;