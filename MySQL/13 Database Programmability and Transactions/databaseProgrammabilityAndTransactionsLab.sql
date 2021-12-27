# PROBLEM 01
use soft_uni;
-- select t.name, count(*) from employees as e
-- join addresses as a
-- on a.address_id = e.address_id
-- join towns as t using (town_id)
-- where t.name = 'Sofia'
-- group by t.name;

# 01 - Първо сменяме делимитъра => ; is default delimiter:
DELIMITER $$
# 02 -  казвам моята схема да е с този делимитър:
USE `soft_uni`$$

# 03 - После правя функцията:
CREATE FUNCTION ufn_count_employees_by_town (town_name VARCHAR(20))
RETURNS INT 
DETERMINISTIC
BEGIN

	DECLARE e_count INT;
    
	SET e_count := (SELECT COUNT(employee_id) FROM employees AS e
	 JOIN addresses AS a ON a.address_id = e.address_id
	 JOIN towns AS t ON t.town_id = a.town_id
	WHERE t.name = town_name);
    
	RETURN e_count;
    
END$$
#пускам в джъдж от create f...до end без $$!
# 04 - Връщам стария делимитър:
DELIMITER ;

#използване на функция:
select ufn_count_employees_by_town ('Sofia') as result;
select 1+2;

#демо - дропване на функция:
drop function ufn_count_employees_by_town;

#01demo myFunction
DELIMITER $$
USE `soft_uni`$$
create function ufn_two_digits_sum (digit_one int, digit_two int)
returns int
deterministic
begin
return (select digit_one + digit_two);
end$$
delimiter ;
select ufn_two_digits_sum (1,2) as result;
drop function ufn_two_digits_sum;

#02demo myFunction
DELIMITER $$
-- USE `soft_uni`$$
create function ufn_two_digits_multiply (digit_one int, digit_two int)
returns int
deterministic
begin
return (select digit_one * digit_two);
end$$
delimiter ;
select ufn_two_digits_multiply (2,7) as result;

# PROBLEM 02
-- select * from employees;
-- update employees
-- set salary = salary * 1.05
-- where department_id = 1;

-- select e.*, d.name from employees as e
-- join departments as d 
-- on e.department_id = d.department_id
-- where d.name = 'Sales';

#business logic
update employees as e
join departments as d 
on e.department_id = d.department_id
set e.salary = 1000
where d.name = 'Sales';
#business logic

-- delimiter ###
-- use soft_uni ###

USE `soft_uni`;
DROP procedure IF EXISTS `usp_raise_salaries`;
DELIMITER $$
USE `soft_uni`$$
create procedure usp_raise_salaries(department_name varchar(50)) 
begin
		#business logic
		update employees as e
		join departments as d 
		on e.department_id = d.department_id
		set e.salary = e.salary * 1.05
		where d.name = department_name;
		#business logic
end$$
DELIMITER ;
call usp_raise_salaries('Sales');

select e.*, d.name from employees as e
join departments as d 
on e.department_id = d.department_id
where d.name = 'Sales';

USE `soft_uni`;
DROP procedure IF EXISTS `usp_raise_salaries`;
DELIMITER $$
USE `soft_uni`$$
create procedure usp_raise_salaries_from_deparment_with_percent (dep_name varchar(50), percent int)
begin
		#business logic
		update employees as e
		join departments as d 
		on e.department_id = d.department_id
		set e.salary = e.salary * (1 + percent / 100)
		where d.name = dep_name;
		#business logic
end$$
DELIMITER ;

call usp_raise_salaries_from_deparment_with_percent ('Sales', 50);

-- select e.*, d.name from employees as e
-- join departments as d 
-- on e.department_id = d.department_id
-- where d.name = 'Sales';

# PROBLEM 03
update employees
set salary = salary * 1.05
where employee_id = 1;

USE `soft_uni`;
DROP procedure IF EXISTS `usp_raise_salary_by_id`;

USE `soft_uni`;
DROP procedure IF EXISTS `soft_uni`.`usp_raise_salary_by_id`;

call usp_raise_salary_by_id (1);

DELIMITER $$
USE `soft_uni`$$
CREATE PROCEDURE `usp_raise_five_times_salary_by_id`(emp_id int)
BEGIN

if ( (select count(*) from employees where employee_id = emp_id) = 1)
then
		update employees
		set salary = salary * 5
		where employee_id = emp_id;
end if;
END$$

DELIMITER ;

call usp_raise_five_times_salary_by_id (1);

# PROBLEM 04
-- (employee_id PK, first_name,last_name,middle_name,job_title,deparment_id,salary)  
create table deleted_employees (
employee_id int primary key,
first_name varchar(50) not null,
last_name varchar(50) not null,
middle_name varchar(50) not null,
job_title varchar(50) not null,
department_id int not null, 
salary decimal(19,4) not null
);





