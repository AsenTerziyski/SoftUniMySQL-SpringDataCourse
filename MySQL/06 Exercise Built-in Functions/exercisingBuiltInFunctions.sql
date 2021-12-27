	#DEMOES
#demo 01
select left('Proba',2);
select right('Proba',2);
#demo 02
select substring('SoftUni', 2,3);
select substring('Softuni', 2);
#demo 03
select replace('SoftUni', 'Soft', '****');
#demo 04
select  ltrim('   SoftUni   ');
select rtrim('   SoftUni   ');
select trim('   SoftUni   ');
#demo 05
select length('qqq');
select length('яяя');
#demo 06
select lower('SoftUni');
select upper('SoftUni');
#demo 07
select reverse('SoftUni');
#demo 08
# връща първия срещнат символ:
select locate('o', 'SoftUni');
# връща първят срещнат след посочената позиция (в случая 3):
select locate('o', 'SoftUniSoft', 3);
#demo 09
select pi();
select round(pi(), 3);
#demo 10
#demo floor and ceil:
select floor(3.978);
select ceil(3.987);
#demo 11
#rand - връща произволна стойност от 0 до 1:
select rand();
# умножавам по 10, 100...
select rand()*10;
select round(rand()*10);
select round(rand()*1000);
#demo 12
#заявка, която показва само четните ID в soft_uni:
select department_id, name from departments
where department_id % 2 = 0;
select department_id, name from departments
where department_id % 2 != 0;
#demo 13
select year(hire_date) from employees
order by employee_id desc;
select day(hire_date) from employees;
select concat('Day:', day(hire_date)) from employees;
select hour(hire_date) from employees;
select timestampdiff(year, hire_date, now()) as 'years in service till now:' from employees;
select timestampdiff(day, hire_date, now()) as 'days in service till now:' from employees;
#demo 14
select date_format(hire_date, '%Y*%b*%D') from employees;
select date_format(hire_date, '%b*%D*%Y') from employees;
#demo 15 
#obtain now:
select now();
select day(now());
select month(now());
select year(now());
#demo 16
select lower(job_title) from employees
where job_title like "%Assistant%"
order by employee_id;

###################################################################################################################################################################
###################################################################################################################################################################
										#E X E R C I S E  B U I L T - I N  F U N C T I O N S

#PROBLEM 01:
		# вариант 1:
select first_name, last_name from employees
where first_name like 'Sa%'
order by employee_id;
		# вариант 2:
select first_name, last_name from employees
where substr(first_name, 1, 2) = 'Sa'
order by employee_id;
		# вариант 3:
select first_name, last_name from employees
where left(first_name, 2) = 'Sa'
order by employee_id;

#PROBLEM 02:
select first_name, last_name from employees
where last_name like '%ei%'
order by employee_id;
		#вариант с регекс:
select first_name, last_name from employees
where last_name regexp('.*ei.*')
order by employee_id;

#PROBLEM 03:
select first_name from employees
where department_id in (3,10) and year(hire_date) between 1995 and 2005
order by employee_id;

#PROBLEM 04:
select first_name, last_name from employees
where job_title not like '%engineer%'
order by employee_id;

#PROBLEM 05:
select name from towns
-- тук много важно в случая е чар_лентд:
where char_length(name) in (5,6)
order by name;

#PROBLEM 06
		#вариант 01:
select town_id,name from towns
where lower(left(name,1)) = 'm' or lower(left(name,1)) = 'k' or lower(left(name,1)) = 'b' or lower(left(name,1)) = 'e'
order by name;
		#вариант 02:
select town_id, name 
from towns
where left(name, 1) in ('M', 'K', 'B', 'E')
order by name;

#PROBLEM 07
select town_id, name 
from towns
where left(name, 1) not in ('R', 'B', '')
order by name;

#PROBLEM 08
create view v_employees_hired_after_2000  as 
select first_name, last_name
from employees
where year(hire_date) > 2000;

#PROBLEM 09
select first_name, last_name 
from employees
where char_length(last_name) = 5;

#PROBLEM 10
select country_name, iso_code
from countries
where country_name like '%A%A%A%'
order by iso_code;

#PROBLEM 11
SELECT 
    peak_name,
    river_name,
    LOWER(CONCAT(peak_name, SUBSTRING(river_name, 2))) AS mix
FROM
    peaks,
    rivers
WHERE
    RIGHT(peak_name, 1) = LEFT(LOWER(river_name), 1)
ORDER BY mix;

#PROBLEM 12
select name, date_format(start, '%Y-%m-%d') from games
where year(start) between 2011 and 2012
order by start, name
limit 50;

#PROBLEM 13
SELECT 
    `user_name`,
    SUBSTRING(`email`, LOCATE('@', `email`) + 1) AS 'Email Provider'
FROM `users`
ORDER BY `Email Provider` , `user_name`;


#PROBLEM 14
SELECT 
    user_name, ip_address
FROM users
WHERE
    ip_address LIKE '___.1%.%.___'
ORDER BY user_name;

#PROBLEM 15
		#Вариант 01:
SELECT 
    name as 'game',
    (CASE
        WHEN HOUR(start) BETWEEN 0 AND 11 THEN 'Morning'
        WHEN HOUR(start) BETWEEN 12 AND 17 THEN 'Afternoon'
        ELSE 'Evening'
    END) AS 'Part Of The Day',
        (case
		when duration<=3 then 'Extra Short'
        when duration <=6 then 'Short'
        when duration <=10 then 'Long'
        else 'Extra Long'
        end
    ) as 'duration'
FROM games;
         
        #Вариант 02:
    SELECT 
    name,
    (CASE
        WHEN HOUR(start) <= 11  THEN 'Morning'
        WHEN HOUR(start) < 18 THEN 'Afternoon'
        ELSE 'Evening'
    END) AS 'Part Of The Day',
    (case
		when duration<=3 then 'Extra Short'
        when duration <=6 then 'Short'
        when duration <=10 then 'Long'
        else 'Extra Long'
        end
    ) as 'duration'
FROM games;
    
#PROBLEM 16
SELECT 
    product_name,
    order_date,
    DATE_ADD(order_date, INTERVAL 3 DAY) AS 'pay_due',
    DATE_ADD(order_date, INTERVAL 1 MONTH) AS 'deliver_due'
FROM orders;









