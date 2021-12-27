#demo01
SELECT 
    d.name AS departmentName,
    d.department_id,
    e.first_name,
    e.last_name,
    ROUND(e.salary, 2) as employee_salary
FROM employees AS e
JOIN departments AS d ON d.department_id = e.department_id
where d.department_id = 1
limit 5;

#demo02
create table test01 (
SELECT 
    d.name AS departmentName,
    d.department_id,
    e.first_name,
    e.last_name,
    ROUND(e.salary, 2) as employee_salary
FROM employees AS e
JOIN departments AS d ON d.department_id = e.department_id
where d.department_id = 1
limit 3
);

#demo03
select e.* from employees as e
left join departments as d
on e.department_id = d.department_id
join addresses as a
on a.address_id = e.address_id
order by e.employee_id;

#demo04
create database demoDataBase01;
use demoDataBase01;

create table t01(
department_id01 int primary key auto_increment,
department_name varchar(50) not null
);
insert into t01 (department_name) 
values
('d01'),
('d02'),
('d03'),
('d04');

create table t02 (
id int primary key auto_increment,
name varchar(50),
department_id02 int,
constraint fk_t02_t01
foreign key (department_id02) references t01(department_id01)
);
insert into t02 (name, department_id02)
values
('name01', 1),
('name01', 2),
('name03', 4),
('name04', 3),
('name05', null),
('name06', null),
('name07', null),
('name08', 3),
('name09', 1),
('Aname', 1),
('Aname', 2),
('Aname', 3),
('Bname', 1),
('Bname', 4),
('Bname', 3);

select name from t02
where name like 'A%'
union
select name from t02
where name like 'B%';

select * from t01 as d
right join t02 as e
on e.department_id02 = d.department_id01;

select * from t01 as d
left join t02 as e
on e.department_id02 = d.department_id01;

select * from t01 as d
join t02 as e
on e.department_id02 = d.department_id01;

select * from t01 as d
join t02 as e
on e.department_id02 = d.department_id01;

select * from t01 as d
inner join t02 as e
on e.department_id02 = d.department_id01;

create table composite_table (
select * from t01 as d
join t02 as e
on e.department_id02 = d.department_id01
);

drop schema demodatabase01;
use soft_uni;

#DEMO05 => UNION
select first_name from employees
where first_name like 'A%'
union
select first_name from employees
where first_name like 'C%'
order by first_name asc;

#demo06
select count(department_id) from departments
group by department_id
having department_id > 2;

#demo06 - composite primary key:
create table demopk (
id int, 
name varchar(50),
primary key (id, name)
);

drop table demopk;

select first_name, (select department_id = 1) from employees;

#demo07 => nested subquiries
select * from employees
where salary > (select avg(salary) from employees)
order by salary asc;

#demo08 => nested subquiries
select employee_id, first_name, job_title, department_id from employees
where salary > (select manager_id from departments where department_id = 2) and lower(job_title) like '%Manager%';
-- select avg(salary) from employees;
################################################################################################################################
#PROBLEM 01
select e.employee_id, concat_ws(' ', e.first_name, e.last_name) as full_name, d.department_id, d.name as department_name
from departments as d
left join employees as e
on d.manager_id = e.employee_id
-- where lower(e.job_title) like '%Manager%'
order by e.employee_id
limit 5;

#PROBLEM 02
select t.town_id, t.name as town_name, a.address_text from addresses as a
left join towns as t
-- using (town_id)
on a.town_id = t.town_id 
where t.name in ('San Francisco', 'Sofia', 'Carnation')
order by town_id, a.address_id;

#PROBLEM 03
select employee_id, first_name, last_name, department_id, salary from employees
where manager_id is null;

#PROBLEM 04
select count(*) as count from employees
where salary > (select avg(salary) from employees);