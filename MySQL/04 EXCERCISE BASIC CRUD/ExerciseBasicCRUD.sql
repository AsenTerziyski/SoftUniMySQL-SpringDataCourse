#pr01
select * from departments
order by department_id;

#pr02
select d.name from departments as d
order by d.department_id;

#pr03
select e.first_name, e.last_name, e.salary from employees as e
order by e.employee_id;

#pr04
select e.first_name, e.middle_name, e.last_name from employees as e
order by e.employee_id;

#pr05
select concat(e.first_name, '.', e.last_name, '@softuni.bg ') as `full_email_address` from employees as e;
-- order by full_email_address;-- 

#pr06
select distinct salary from employees;

#pr07
select * from employees
where job_title = 'Sales Representative';

#pr08
select e.first_name, e.last_name, e.job_title as JobTitle from employees as e
where e.salary between 20000 and 30000
order by e.employee_id;

#pr09
select concat_ws(' ', e.first_name, e.middle_name, e.last_name) as `Full Name` from employees as e
where e.salary in (25000, 14000, 12500,23600);

#pr10
select e.first_name, e.last_name from employees as e where manager_id is null;

#pr11
select e.first_name, e.last_name, e.salary from employees as e
where e.salary > 50000
order by e.salary desc;

#pr12
select e.first_name, e.last_name from employees as e
order by e.salary desc
limit 5;

create view 5BestPaidEmployees as (select e.first_name, e.last_name from employees as e
order by e.salary desc
limit 5);

create table testTable ( select * from 5BestPaidEmployees );

alter table testtable

rename to 5TopPaidEmpll;

#pr13
select e.first_name, e.last_name from employees as e
where e.department_id !=4;

#pr14
select * from employees as e
order by 
e.salary desc,
e.first_name,
e.last_name desc, 
e.middle_name,
e.employee_id;

#pr15
create view `ViewEmployeesWithSalaries`  as (
select e.first_name, e.last_name, e.salary from employees as e
);
create table testTable02 (select * from ViewEmployeesWithSalaries );

#pr16
create view `ViewEmployeesWithJobTitles` as (
select concat_ws(' ', e.first_name, e.last_name) as full_name, job_title from employees as e
);

create table `testtable03ViewEmployeesWithJobTitles` (
select * from ViewEmployeesWithJobTitles
limit 5
);

create table testtable03_01(
select v.full_name from ViewEmployeesWithJobTitles as v
);
-- drop table testtable03ViewEmployeesWithJobTitles;

#pr17
select distinct job_title from employees as e
order by e.job_title;

#pr18
select p.project_id, p.name, p.description, p.start_date, p.end_date from projects as p
order by p.start_date, p.name, p.project_id
limit 10;

create table `testtable03` (
select p.project_id, p.start_date, p.end_date from projects as p
order by p.start_date, p.name, p.project_id
limit 10
);

alter table testtable03
rename to testtable04;

#pr19
select e.first_name, e.last_name, e.hire_date from employees as e
order by e.hire_date desc
limit 7;

create table testtable05 (
select e.first_name, e.last_name, e.hire_date from employees as e
order by e.hire_date desc
limit 3
);

#pr20
update employees
set salary = salary * 1.12
where department_id in (12,4,46,42);
select salary from employees;

#pr21
select p.peak_name from peaks as p
order by p.peak_name asc;

#pr22
select c.country_name, c.population from countries as c
where continent_code = 'EU'
order by c.population desc;

#pr23
SELECT 
    c.country_name,
    c.country_code,
    IF(c.currency_code = 'EU', 'Euro', ' Not euro') AS currency
FROM
    countries AS c
ORDER BY c.country_name;

drop schema geography;

#pr24
select c.name from characters as c
order by c.name asc;






