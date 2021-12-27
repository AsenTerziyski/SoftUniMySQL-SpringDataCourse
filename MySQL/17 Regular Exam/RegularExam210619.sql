######SECTION 1

#RPOBLEM01
create database stc;
use stc;
#PROBLEM01
#create tables

create table addresses (
	id int primary key auto_increment,
	name varchar(100) not null
);

create table categories (
	id int primary key auto_increment,
	name varchar(10) not null
);

create table clients (
	id int primary key auto_increment,
	full_name varchar(50) not null,
	phone_number varchar(20) not null
);

create table drivers (
	id int primary key auto_increment,
	first_name varchar(30) not null,
	last_name varchar(30) not null,
	age int not null,
	rating float default 5.5
);

create table cars (
	id int primary key auto_increment,
	make varchar(20) not null,
	model varchar(20),
	year int not null default 0,
	mileage int default 0,
	`condition` char(1) not null,
	category_id int not null,
	constraint fk_cars_categories
	foreign key (category_id) references categories(id)
);

create table courses (
id int primary key auto_increment,
from_address_id int not null,
start datetime not null,
bill decimal(10,2) default 10,
car_id int not null,
client_id int not null,
constraint fk_courses_address
foreign key (from_address_id) references addresses(id),
constraint fk_courses_cars
foreign key (car_id) references cars(id),
constraint fk_courses_clients
foreign key (client_id) references clients(id)
);

create table cars_drivers (
car_id int not null,
driver_id int not null,

constraint pk_cars_drivers
primary key (car_id, driver_id),

constraint fk_cars_drivers_cars
foreign key (car_id) references cars(id),

constraint fk_cars_drivers_drivers
foreign key (driver_id) references drivers(id)
);

-- ############################################################################################################################
-- ######SECTION 2
-- #PROBLEM02
-- insert into  clients (full_name, phone_number)
-- select concat_ws(' ', d.first_name, d.last_name) as full_name, concat('(088) 9999', d.id*2 ) from drivers as d
-- where d.id between 10 and 20;

-- #PROBLEM03
-- update cars 
-- set `condition` = 'C'
-- where (mileage >=800000 or  mileage is null) and year <= 2010 and make != 'Mercedes-Benz';

-- #PROBLEM04
delete from clients
where char_length(full_name) > 3 and id not in (select client_id from courses );

############################################################################################################################
######SECTION 3

#PROBLEM05
select make, model, `condition` from cars
order by id;

#PROBLEM06
select d.first_name, d.last_name, c.make, c.model, c.mileage from drivers as d
join cars_drivers as cd
on cd.driver_id = d.id
join cars as c
on c.id = cd.car_id
where c.mileage is not null
order by c.mileage desc, d.first_name;

#PROBLEM07
select c.id, c.make, c.mileage, count(crs.car_id) as `count_of_courses`, round(avg(crs.bill),2) as avg_bill from cars as c
left join courses as crs
on crs.car_id = c.id
group by c.id
having `count_of_courses` !=2
order by `count_of_courses` desc, c.id;

#PROBLEM08
select cl.full_name, count(c.car_id) as count_of_cars, sum(c.bill) as total_sum from clients as cl
join courses as c 
on cl.id = c.client_id
where substr(full_name, 2,1) = 'a'
group by cl.id
having count(c.car_id) > 1
order by cl.full_name asc;

#PROBLEM09
select a.name,  if((hour(c.start)>=6 and hour(c.start)<=20), 'Day', 'Night') as day_time, c.bill,
clnts.full_name, crs.make, crs.model, cat.name
from courses as c
join addresses as a
on a.id = c.from_address_id
join clients as clnts
on clnts.id = c.client_id
join cars as crs
on crs.id = c.car_id
join categories as cat
on cat.id = crs.category_id
order by c.id;

############################################################################################################################
######SECTION 4 Programmability

#PROBLEM10
select count(crs.id) from clients as c
join courses as crs
on crs.client_id = c.id
where c.phone_number = '(803) 6386812';

delimiter $$
create function udf_courses_by_client (fphone_num VARCHAR (20))
returns int
deterministic
begin
return (
select count(crs.id) from clients as c
join courses as crs
on crs.client_id = c.id
where c.phone_number = fphone_num
);
end$$
delimiter ;
SELECT udf_courses_by_client ('(803) 6386812') as `count`; 
SELECT udf_courses_by_client ('(831) 1391236') as `count`;

# 01 create functions 
-- delimiter $$
-- create function udf_name (......)
-- returns int
-- deterministic
-- begin
-- end$$
-- delimiter ;

#PROBLEM11
-- select a.name, cl.full_name,
-- (case
-- 	when crs.bill>0 and crs.bill<=20.00 then 'Low'
-- 	when crs.bill>20.00 and crs.bill<=30.00 then 'Medium'
-- 	when crs.bill>30.00 then 'High'
-- end
-- )  as level_of_bill, cars.make, cars.`condition`, cat.name as cat_name
-- from addresses as a 
-- join courses as crs
-- on crs.from_address_id = a.id
-- join cars as cars
-- on cars.id = crs.car_id
-- join categories as cat
-- on cat.id = cars.category_id
-- join clients as cl
-- on cl.id = crs.client_id
-- where a.name = '700 Monterey Avenue'
-- order by cars.make, cl.full_name;

delimiter $$
create procedure udp_courses_by_address(addrss varchar (100))
begin
select a.name, cl.full_name,
(case
	when crs.bill > 0 and crs.bill <= 20.00 then 'Low'
	when crs.bill > 20.00 and crs.bill <= 30.00 then 'Medium'
	when crs.bill > 30.00 then 'High'
end
)  as level_of_bill,
cars.make, cars.`condition`, cat.name as cat_name
from addresses as a 
join courses as crs
on crs.from_address_id = a.id
join cars as cars
on cars.id = crs.car_id
join categories as cat
on cat.id = cars.category_id
join clients as cl
on cl.id = crs.client_id
where a.name = addrss
order by cars.make, cl.full_name;
end$$
delimiter ;

CALL udp_courses_by_address('700 Monterey Avenue');

-- delimiter $$
-- create procedure udp_courses_by_address(addrss varchar (100))
-- begin
-- select a.name, cl.full_name,
-- (case
-- when crs.bill<=20 then 'Low'
-- when crs.bill<=30 then 'Medium'
-- else 'High'
-- end
-- )  as level_of_bill, cars.make, cars.`condition`, cat.name as cat_name
-- from addresses as a 
-- join courses as crs
-- on crs.from_address_id = a.id
-- join cars as cars
-- on cars.id = crs.car_id
-- join categories as cat
-- on cat.id = cars.category_id
-- join clients as cl
-- on cl.id = crs.client_id
-- where a.name = '700 Monterey Avenue'
-- order by cars.make, cl.full_name;
-- end$$
-- delimiter ;

CALL udp_courses_by_address('700 Monterey Avenue');

-- # 02 create procedures
-- delimiter $$
-- create procedure udp_.....(........)
-- begin
-- end$$
-- delimiter ;
############################################################################################################################