#DEMOES:
#demo01 - no aggregation
select department_id, 
sum(salary) as total_sum
from employees;
#demo02 - with aggregation
select department_id, sum(salary) as department_salary from employees
group by department_id
order by sum(salary);
#demo03
select department_id, count(id) as employees_in_department from employees
group by department_id
order by count(id);
#demo04
create view dep_employees_salariesdep_employees_salaries as (
select department_id, count(id) as employees_in_department, sum(salary) as department_salary from employees
group by department_id
order by count(id), sum(salary)
);
#demo05
select department_id, count(id) as employees_in_department from employees
group by department_id
having count(id) > 2
order by count(id);
#demo06
create table `employees_test` (
id int primary key auto_increment,
name varchar(10), 
grade float,
department_id int,
office_id int
);
drop table `employees_test`;
#demo07
select sum(salary) from employees;
#demo08
select department_id, sum(salary) as department_sum_salaries from employees
group by department_id
order by department_sum_salaries;
#demo09
select department_id, min(salary) as department_min_salaries from employees
group by department_id
order by department_min_salaries;
#demo10
select id, avg(price) as avg_price from products;
#demo11
select id, round(avg(price),2) as avg_price from products
group by category_id, 'avg_price'
order by id;

#EXERCISE PROBLEMS
#############################################

#PROBLEM01
select count(*) from wizzard_deposits;
#############################################

#PROBLEM02
#Вариант01
select magic_wand_size from wizzard_deposits
order by magic_wand_size desc
limit 1;
#Вариант02
select max(magic_wand_size) from wizzard_deposits;
#############################################

#PROBLEM03
-- select deposit_group, max(magic_wand_size) as 'longest_magic_wand'
-- from wizzard_deposits
-- group by deposit_group
-- order by 'longest_magic_wand', deposit_group;

# да внимавам с кавичките:
select `deposit_group`, max(`magic_wand_size`) as `longest_magic_wand`
from `wizzard_deposits`
group by `deposit_group`
order by `longest_magic_wand`, `deposit_group`;
#############################################

#PROBLEM04
select `deposit_group`
from `wizzard_deposits`
group by `deposit_group`
order by avg(`magic_wand_size`)
limit 1;
#############################################

#PROBLEM05
select `deposit_group`, sum(`deposit_amount`) as `total_sum` from `wizzard_deposits`
group by `deposit_group`
order by `total_sum` asc;
#############################################

#PROBLEM06
select `deposit_group`, sum(`deposit_amount`) as `total_sum` 
from `wizzard_deposits`
#мястото на where е след фром и преди грууп:
where `magic_wand_creator` = 'Ollivander family'
group by `deposit_group`
order by `deposit_group`;
#############################################

#PROBLEM07
select `deposit_group`, sum(`deposit_amount`) as `total_sum`
from `wizzard_deposits`
#мястото на where е след фром и преди грууп:
where `magic_wand_creator` = 'Ollivander family'
group by `deposit_group`
having `total_sum` < 150000
order by `total_sum` desc;
#############################################

#PROBLEM08
select `deposit_group`, `magic_wand_creator`, min(`deposit_charge`) as `min_deposit_charge`
from wizzard_deposits
group by deposit_group, magic_wand_creator
order by magic_wand_creator asc, deposit_group;

#проба без кавички:
select deposit_group, magic_wand_creator, min(deposit_charge) as min_deposit_charge
from wizzard_deposits
group by deposit_group, magic_wand_creator
order by magic_wand_creator asc, deposit_group;

-- select `deposit_group`, `magic_wand_creator`, min(`deposit_charge`) as `min_deposit_charge`
-- from wizzard_deposits
-- group by deposit_group
-- order by magic_wand_creator asc, deposit_group;
#############################################

#PROBLEM09
SELECT (CASE
		#between включва двете граници:
        WHEN age BETWEEN 0 AND 10 THEN '[0-10]'
        WHEN age BETWEEN 11 AND 20 THEN '[11-20]'
        WHEN age BETWEEN 21 AND 30 THEN '[21-30]'
        WHEN age BETWEEN 31 AND 40 THEN '[31-40]'
        WHEN age BETWEEN 41 AND 50 THEN '[41-50]'
        WHEN age BETWEEN 51 AND 60 THEN '[51-60]'
        ELSE '[61+]'
    END) AS age_group, count(*) as wizard_count
FROM wizzard_deposits
group by age_group
order by age_group;

SELECT (CASE
		#between включва двете граници:
        WHEN age BETWEEN 0 AND 10 THEN '[0-10]'
        WHEN age BETWEEN 11 AND 20 THEN '[11-20]'
        WHEN age BETWEEN 21 AND 30 THEN '[21-30]'
        WHEN age BETWEEN 31 AND 40 THEN '[31-40]'
        WHEN age BETWEEN 41 AND 50 THEN '[41-50]'
        WHEN age BETWEEN 51 AND 60 THEN '[51-60]'
        ELSE '[61+]'
    END) 
    # beetter count columns that are not null:
    AS age_group, count(id) as wizard_count
FROM wizzard_deposits
group by age_group
order by age_group;
#############################################

#PROBLEM10
#Write a query that returns all unique wizard first letters of their first names only if they have deposit of type Troll Chest.
#Order them alphabetically. Use GROUP BY for uniqueness.

#Вариант01
SELECT 
    SUBSTR(first_name, 1, 1) AS first_letter
FROM
    wizzard_deposits
WHERE
    deposit_group = 'Troll Chest'
GROUP BY first_letter
ORDER BY first_letter;

#Вариант02
SELECT 
    LEFT(first_name, 1) AS first_letter
FROM
    wizzard_deposits
WHERE
    deposit_group = 'Troll Chest'
GROUP BY first_letter
ORDER BY first_letter;

#Вариант03
select distinct left(first_name, 1) as first_letter from wizzard_deposits
where deposit_group = 'Troll Chest'
order by first_letter;

SELECT 
    RIGHT(first_name, 100) AS last_letter
FROM
    wizzard_deposits
WHERE
    deposit_group = 'Troll Chest'
GROUP BY last_letter
ORDER BY last_letter;
#############################################

#PROBLEM11
SELECT 
    deposit_group,
    is_deposit_expired,
    AVG(deposit_interest) AS average_interest
FROM
    wizzard_deposits
WHERE
    deposit_start_date > '1985-01-01'
GROUP BY deposit_group , is_deposit_expired
ORDER BY deposit_group DESC , is_deposit_expired ASC;
#############################################

#PROBLEM12
select department_id, min(salary) as minimum_salary
from employees
where department_id in (2,5,7) and year(hire_date) > 2000-01-01
group by department_id
order by department_id asc;
#############################################

#PROBLEM13
create table `edited_employees_table`
(
select * from employees
where salary > 30000 and manager_id !=42
);

update edited_employees_table
set salary = salary + 5000
where department_id = 1;

select department_id, avg(salary) as avg_salary from edited_employees_table
group by department_id
order by department_id;
#############################################

#PROBLEM14
select department_id, max(salary) as max_salary from employees
group by department_id
having max_salary < 30000 or max_salary>70000
order by department_id;
#############################################

#PROBLEM15
select count(employee_id) from employees
where manager_id is null;
#############################################

#PROBLEM16
select e.department_id, 
#нестната завка - е2:
	(	select distinct e2.salary from employees as e2
		where e2.department_id = e.department_id
		order by e2.salary desc
		limit 1 offset 2	) as third_highest_salary
        
from employees as e
group by e.department_id
having third_highest_salary is not null
order by e.department_id;

-- select distinct department_id, salary from employees
-- where department_id = 1
-- order by salary desc
-- limit 1 offset 2;
#############################################

#PROBLEM17
select e.first_name, e.last_name, e.department_id
from employees as e
where e.salary > 
(

select avg(e2.salary)
from employees  as e2
where e2.department_id = e.department_id

)
order by e.department_id, e.employee_id
-- limit 10 offset 3;
limit 10;
-- select avg(salary) as das from employees 
-- where department_id = 1;
#############################################

#PROBLEM18
select department_id, sum(salary) as total_salary from employees
group by department_id
order by department_id;
#############################################










