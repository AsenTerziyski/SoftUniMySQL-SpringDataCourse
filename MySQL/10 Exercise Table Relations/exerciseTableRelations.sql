#PROBLEM01
#1.	One-To-One Relationship:
CREATE DATABASE test01;
USE test01;

CREATE TABLE passports (
    passport_id INT PRIMARY KEY AUTO_INCREMENT,
    passport_number VARCHAR(50) UNIQUE NOT NULL
);

# да започва от 101 в случая:
alter table passports auto_increment = 101;

insert into  passports (passport_number)
values
('N34FG21B'),
('K65LO4R7'),
('ZE657QP2');

CREATE TABLE people (
    person_id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(30),
    salary DECIMAL(10 , 2 ),
    passport_id INT UNIQUE,
    CONSTRAINT fk_people_passports FOREIGN KEY (passport_id) REFERENCES passports (passport_id)
);

/*
first_name	salary	passport_id
Roberto    43300.00	    102
Tom	       56100.00 	103
Yana	    60200.00	101
*/
insert into people 
values
(1, 'Roberto', 43300.00, 102),
(2, 'Tom', 56100.00, 103),
(3, 'Yana', 60200.00, 101);

#PROBLEM02
#2.	One-To-Many Relationship:
CREATE DATABASE test02;
USE test02;

CREATE TABLE manufacturers (
    manufacturer_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    established_on DATE
);

insert into manufacturers (name, established_on)
values
/*
BMW    	01/03/1916
Tesla	01/01/2003
Lada	01/05/1966
*/
('BMW', '1916-03-01' ),
('Tesla', '2003-01-01' ),
('Lada', '1966-05-01' );
-- truncate manufacturers;

CREATE TABLE models (
    model_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    manufacturer_id INT,
    CONSTRAINT fk_models_manufacturers
    FOREIGN KEY (manufacturer_id) REFERENCES manufacturers (manufacturer_id)
)  AUTO_INCREMENT=101;

insert into models (name, manufacturer_id )
values
/*
X1	1
i6	1
Model S	2
Model X	2
Model 3	2
Nova	3
*/
('X1',1),
('i6',1),
('Model S',2),
('Model X',2),
('Model 3',2),
('Nova',3);

#PROBLEM03
#2.	One-To-Many Relationship:
CREATE DATABASE test03;
USE test03;

CREATE TABLE exams (
    exam_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL
)  AUTO_INCREMENT=101;
# може и така:
#alter table exams auto_increment = 101;

insert into exams (name)
values
/*
Spring MVC
Neo4j
Oracle 11g
*/
('Spring MVC'),
('Neo4j'),
('Oracle 11g');

CREATE TABLE students (
    student_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL
);

insert into students (name)
values
/*
Mila                                      
Toni
Ron
*/
('Mila'),
('Toni'),
('Ron');

CREATE TABLE students_exams (
    student_id INT,
    exam_id INT,
    # композитен ключ:    
    CONSTRAINT pk_students_exams PRIMARY KEY (student_id , exam_id),
    # първи праймъри кий:
    CONSTRAINT fk_students_exams_students FOREIGN KEY (student_id) REFERENCES students (student_id),
    # втори праймъри кий:
    CONSTRAINT fk_students_exams_exams FOREIGN KEY (exam_id) REFERENCES exams (exam_id)
);

insert into students_exams
values
/*
1	101
1	102
2	101
3	103
2	102
2	103
*/
(1,101),
(1,102),
(2,101),
(3,103),
(2,102),
(2,103);

#PROBLEM04
#4.	Self-Referencing
CREATE DATABASE test04;
USE test04;

create table teachers (
teacher_id int primary key auto_increment,
name varchar(50) not null,
manager_id int
# най-напред да попълня таблицата и след това алтър тейбъл:
-- constraint fk_manager_id_teacher_id
-- foreign key (manager_id) references teachers(teacher_id)
) auto_increment = 101;

/*
John	
Maya	106
Silvia	106
Ted 	105
Mark	101
Greta	101
*/
insert into teachers (name, manager_id)
values
('John', null),
('Maya', 106),
('Silvia', 106),
('Ted', 105),
('Mark', 101),
('Greta', 101);

alter table teachers
add constraint fk_manager_id_teacher_id
foreign key (manager_id) references teachers(teacher_id);

#ako iskam da smenya imeto na John s TestJohn:
update teachers
set name = 'TestJohn'
where teacher_id = 101;

#PROBLEM05
#5.	Online Store Database:
CREATE DATABASE test05;
USE test05;

create table cities (
	city_id int(11) primary key auto_increment,
	name varchar(50)
);

create table customers (
	customer_id int primary key auto_increment,
    name varchar(50),
    birthday date,
    city_id int,
    constraint fk_customers_cities
    foreign key (city_id) references cities(city_id)
);

create table orders (
	order_id int primary key auto_increment,
	customer_id int,
	constraint fk_oreders_customers
	foreign key (customer_id) references customers(customer_id)
);

create table item_types (
	item_type_id int primary key auto_increment,
    name varchar(50)
);

create table items (
	item_id int primary key auto_increment,
	name varchar(50),
	item_type_id int,
	constraint fk_items_item_types
	foreign key (item_type_id) references item_types(item_type_id)
);

create table order_items (
	order_id int,
	item_id int,
	constraint pk_order_items
	primary key (order_id,item_id ),
	constraint fk_order_items_orders
	foreign key (order_id) references orders(order_id),
	constraint fk_order_items_items
	foreign key (item_id) references items(item_id)
);

#PROBLEM06
CREATE DATABASE test06;
USE test06;

create table majors (
	major_id int primary key auto_increment,
    name varchar(50)
);

create table students (
	student_id int primary key auto_increment,
    student_number varchar(12),
    student_name varchar(50),
    major_id int,
    constraint fk_students_majors
    foreign key (major_id) references majors(major_id)
);

create table payments (
	payment_id int primary key auto_increment,
    payment_date date,
    payment_amount decimal(8,2),
    student_id int,
    constraint fk_payments_students
    foreign key (student_id) references students(student_id)
);

create table subjects (
	subject_id int primary key auto_increment,
    subject_name varchar(50)
);

create table agenda (
student_id int,
subject_id int,
constraint pk_agenda_students_subjects
primary key (student_id,subject_id ),
constraint fk_agenda_students
foreign key (student_id) references students(student_id),
constraint fk_agenda_subjects
foreign key (subject_id) references subjects(subject_id)
);

select m.*, s.student_id,s.student_name, s.student_number, p.payment_id, p.payment_amount 
from majors as m
join students as s on s.major_id = m.major_id
join payments as p on p.student_id = s.student_id;

create table test_table_problem06 (
select m.*, s.student_id,s.student_name, s.student_number, p.payment_id, p.payment_amount 
from majors as m
join students as s on s.major_id = m.major_id
join payments as p on p.student_id = s.student_id
);

#PROBLEM09
select m.mountain_range, p.peak_name, p.elevation from mountains as m
join peaks as p
on m.id = p.mountain_id
where m.mountain_range = 'Rila'
order by p.elevation desc;

#join 3 tables:
select m.mountain_range, p.peak_name, p.elevation from mountains as m
join peaks as p on m.id = p.mountain_id
join mountains_countries as mc on mc.mountain_id = m.id
where m.mountain_range = 'Rila'
order by p.elevation desc;

select m.id, peak_name, p.elevation, mountain_range, country_code from mountains as m
join peaks as p on m.id = p.mountain_id
join mountains_countries as mc on mc.mountain_id = m.id
	-- where m.mountain_range = 'Rila' or m.mountain_range = 'Vitosha'
order by p.elevation desc;

#demo create table after join:
create table testTable (
select m.id, peak_name, p.elevation, mountain_range, country_code from mountains as m
join peaks as p on m.id = p.mountain_id
join mountains_countries as mc on mc.mountain_id = m.id
	-- where m.mountain_range = 'Rila' or m.mountain_range = 'Vitosha'
order by p.elevation desc
);

create table testTable2 as select m.id, peak_name, p.elevation, mountain_range, country_code from mountains as m
join peaks as p on m.id = p.mountain_id
join mountains_countries as mc on mc.mountain_id = m.id
	-- where m.mountain_range = 'Rila' or m.mountain_range = 'Vitosha'
order by p.elevation desc;

#PROBLEM06 - EXERCISING:
CREATE DATABASE test06_test;
USE test06_test;

create table majors (
	major_id int primary key auto_increment,
    name varchar(50)
) auto_increment = 1000;

insert into majors (name)
values
('Major 1'),
('Major 2'),
('Major 3');

create table students (
	student_id int primary key auto_increment,
    student_number varchar(12) default 'xxx',
    student_name varchar(50),
    major_id int,
    constraint fk_students_majors
    foreign key (major_id) references majors(major_id)
) auto_increment = 100;

insert into students (student_name, major_id)
values
('StudentName 1', 1000),
('StudentName 2', 1001),
('StudentName 3', 1002);

create table payments (
	payment_id int primary key auto_increment,
    payment_date timestamp default current_timestamp,
    payment_amount decimal(8,2),
    student_id int,
    constraint fk_payments_students
    foreign key (student_id) references students(student_id)
);

insert into payments (payment_amount, student_id)
values
(100.27, 100),
(10000.997, 101),
(500.27, 102);

create table subjects (
	subject_id int primary key auto_increment,
    subject_name varchar(50)
) auto_increment = 501;

insert into subjects (subject_name) 
values
('Math'),
('Geodesy'),
('Watter');

create table agenda (
student_id int,
subject_id int,
constraint pk_agenda_students_subjects
primary key (student_id,subject_id ),
constraint fk_agenda_students
foreign key (student_id) references students(student_id),
constraint fk_agenda_subjects
foreign key (subject_id) references subjects(subject_id)
);

insert into agenda (student_id, subject_id)
values
(100, 502),
(102,501),
(101,503);

select m.*, s.student_id,s.student_name, s.student_number, p.payment_id, p.payment_amount , a.subject_id
from majors as m
join students as s on s.major_id = m.major_id
join payments as p on p.student_id = s.student_id
join agenda as a on a.student_id = s.student_id
order by a.student_id;

create table test_table_problem06 (
select m.*, s.student_id,s.student_name, s.student_number, p.payment_id, p.payment_amount 
from majors as m
join students as s on s.major_id = m.major_id
join payments as p on p.student_id = s.student_id
);

create table test_table02_promblem06 (
select m.*, s.student_id,s.student_name, s.student_number, p.payment_id, p.payment_amount , a.subject_id
from majors as m
join students as s on s.major_id = m.major_id
join payments as p on p.student_id = s.student_id
join agenda as a on a.student_id = s.student_id
order by a.student_id
);


















