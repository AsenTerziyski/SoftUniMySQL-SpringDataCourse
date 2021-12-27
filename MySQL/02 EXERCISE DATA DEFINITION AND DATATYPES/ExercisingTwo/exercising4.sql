select * from towns;
select * from departments;
select * from employees;

select * from towns
order by name;

select * from employees
order by first_name asc;

select concat_ws('-', first_name, last_name) as full_name from employees
order by  first_name desc;

create table proba_employees (
select concat_ws('-', first_name, last_name) as full_name, id from employees
order by  first_name desc
);

drop table proba_employees;

update employees
set salary = salary + 1000000
where id = 3;

select first_name, salary from employees;

truncate proba_employees;