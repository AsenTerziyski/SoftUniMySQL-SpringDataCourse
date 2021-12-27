#####################################################################################################################################
		#01 DEMOES
#demo01
select id, round(avg(salary),0) as avg_salary, count(id) as count_employee
from employees
group by department_id
having avg_salary > 500;
#demo02
SELECT e.`first_name`, count(id)
FROM `employees` AS e
GROUP BY e.`first_name`;
#demo03
SELECT 
    e.department_id, e.first_name,
    COUNT(e.id) AS employees_in_department,
    ROUND(AVG(e.salary), 0) AS avg_salary
FROM
    employees AS e
GROUP BY e.department_id, e.first_name
having avg_salary > 1000
order by avg_salary;
#demo04
select empl.id, concat_ws(' ', empl.first_name, empl.last_name) as full_name,
empl.department_id, sum(empl.salary) as total_dep_salary
from employees as empl
group by empl.department_id, empl.first_name
having total_dep_salary > 1561
order by total_dep_salary;
#demo05
select e.department_id, min(e.salary) as min_salary from employees as e
group by e.department_id;
#demo05
select department_id, count(id) from employees
where department_id > 1
group by department_id
having count(id) > 3
order by department_id;
#demo06
select a1.department_id, sum(salary) as total_salary from employees as a1
group by a1.department_id
having total_salary > 	100
order by department_id;
#demo07
select * from books
group by author_id;
#demo08
select count(*), title from books
group by author_id;
#demo09
explain select department_id, round((sum(salary))/count(department_id), 2) as 'total salary' from employees
group by department_id;
#demo10
select * from employees as e, departments as d;
#demo11 => count()
select department_id, count(*) from employees
group by department_id
order by department_id;
#demo12
select department_id, sum(salary) from employees
group by department_id
order by department_id;
#demo13 => group_concat and having:
select department_id, group_concat(first_name) as persons, count(*) as 'NumberOFEmployees' 
from employees
--  where е преди групирането:
where first_name != 'Michael'
group by department_id
-- having count(*) > 2
order by department_id desc, 'NumberOFEmployees';
#demo14 => demo offset:
select * from employees
order by id
limit 5 offset 2;

		##02 LAB PROBLEMS
################################################################
#PROBLEM 01
#Departments Info
select department_id, count(*) as 'Number of employees' from employees
group by department_id;
-- order by department_id desc;
-- having count(*) > 2;
################################################################
#PROBLEM 02
#Average Salary
	#вариант №1
select department_id, round((sum(salary))/count(department_id), 2) as 'total salary' from employees
group by department_id;
	#вариант №2
select department_id, round(avg(salary),2) as 'Average salary' from employees
group by department_id
-- having avg(salary) > 2000
;
################################################################
#PROBLEM03
#Min Salary - най-напред групираме по мин и после даваме having:
SELECT 
    department_id, ROUND(MIN(salary), 2) AS 'Min Salary'
FROM
    employees
GROUP BY department_id
HAVING `Min Salary` > 800
ORDER BY department_id;
################################################################
#PROBLEM04
#Appetizers Count
	#Вариант1:
SELECT 
    COUNT(*)
FROM
    products
WHERE
    category_id = 2 AND price > 8;

	#Вариант2:
SELECT 
    COUNT(*)
FROM
    products
WHERE
    price > 8
GROUP BY category_id
HAVING category_id = 2;
################################################################
#PROBLEM05
#Menu Prices
SELECT 
    category_id,
    ROUND(AVG(price), 2) AS 'Average Price',
    ROUND(MIN(price), 2) AS 'Cheapest Product',
    ROUND(MAX(price), 2) AS 'Most Expensive Product'
FROM
    products
GROUP BY category_id;
################################################################
#PROBLEM12 from Exercises:
select department_id, min(salary) from employees
-- where year(hire_date) > 
where hire_date > '2000-01-01 00:00:00.000000'
group by department_id
having deparment_id in (2,5,7)
order by department_id asc;
################################################################