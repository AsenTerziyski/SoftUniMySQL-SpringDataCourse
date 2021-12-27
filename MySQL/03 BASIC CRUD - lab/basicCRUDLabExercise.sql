#problem 01:
select e.id, e.first_name, e.last_name, e.job_title from employees as e;

#problem 02:
#Select Employees with Filter
select e.id, concat(' ', e.first_name, e.last_name) as full_name, e.job_title, round(e.salary, 2) from employees as e
where e.salary > 1000.00
order by e.id;

#problem 03:
#Update Employees Salary
select salary, job_title from employees
where job_title = 'Manager';
update employees
set salary = salary + 100
where job_title = 'Manager';

#problem 04
#Top Paid Employee
select e.id, e.first_name, e.last_name, e.job_title, e.department_id, e.salary from employees as e
order by e.salary desc
limit 1;
select e.id, concat(e.first_name, ' ', e.last_name, ' with ', e.job_title, ' and department id: ', e.department_id) as FULL_EMPLOYEE_INFO, e.salary from employees as e
order by e.salary desc
limit 3;

#problem 05
#Select Employees by Multiple Filters
select * from employees
where department_id = 4 and salary >= 1000;

#problem 06
#Delete from Table
delete from employees
where department_id in (2,1);
select * from employees;

#demoes from PRESENTATION
select concat_ws('+',e.first_name, e.last_name), e.salary as full_name_with_plus_separator from employees as e
where e.salary > 990 and e.salary =1600;

insert into employees (first_name, last_name, job_title, department_id, salary)
values
('Xxxxxxx1', 'Yuuuuuuu1', 'ucker', 1,10000.025),
('Xxxxxxx1', 'Yuuuuuuu1','ucker', 2,10000.025),
('Xxxxxxx1', 'Yuuuuuuu1','ucker', 3,10000.025),
('Xxxxxxx1', 'Yuuuuuuu1', 'ucker',3,10000.025);

delete from employees
where job_title = '%ucker%';

select job_title, round(salary, 2) from employees;

update employees
set job_title = 'Lazy'
where id  in (1,2,3);
select id, job_title from employees
order by id;

select e.id as №, concat_ws(' ', e.first_name, e.last_name) as FullName from employees as e
order by FullName desc;

select distinct department_id from employees;
select id, (select distinct department_id) , id, first_name, last_name, salary from employees
order by id, department_id;

select id, department_id, last_name from employees
where department_id not in (1,4)
order by id;

select * from employees as e
where e.department_id = 4 and e.salary >=1000;

select * from employees
where department_id is null;

select * from employees
where department_id is not null;

#create VIEW
create view testView as  (
select e.id as №, concat_ws(' ', e.first_name, e.last_name) as FullName from employees as e
order by FullName desc
);

select * from testView;

# CREATE TABLE FROM VIEW:
create table testTable (
select * from testView
);

create view topPaidEmployee as (
select * from employees
order by salary desc
limit 1
);

create table testTableTopPaidEmployee (
select last_name, salary from topPaidEmployee
);

CREATE TABLE clients_rooms 
AS SELECT CONCAT_WS(' ', first_name, last_name) AS fullName, room_id FROM clients;

update clients_rooms
set fullName = 'Xxxxxx Petrov'
where fullName = 'Pesho Petrov' or room_id = 2;

delete from clients_rooms
where room_id = 1;

truncate clients_rooms;