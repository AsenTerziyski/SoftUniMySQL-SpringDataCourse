#######################################################################################################
#PROBLEM 01
#Employee Address
#Вариант №1:
select employee_id, job_title, a.address_id, a.address_text from employees as e
join addresses as a
on e.address_id = a.address_id
order by a.address_id asc
limit 5;
#Варинат №2:
select employee_id, job_title, a.address_id, a.address_text from employees as e
join addresses as a
using (address_id)
order by a.address_id asc
limit 5;
#######################################################################################################
#PROBLEM 02
#addresses with Towns
select e.first_name, e.last_name, t.name, a.address_text from employees as e
join addresses as a
on e.address_id = a.address_id
join towns as t
on t.town_id = a.town_id
order by e.first_name asc, e.last_name
limit 5;
#######################################################################################################
#PROBLEM 03
#Sales Employee
select e.employee_id, e.first_name, e.last_name, d.name from employees as e
join departments as d
on e.department_id = d.department_id
where d.name = 'Sales'
order by employee_id desc;
#######################################################################################################
#PRPOBLEM 04
#Employee Departments
select e.employee_id, e.first_name, e.salary, d.name from employees as e
join departments as d
on d.department_id = e.department_id
where e.salary > 15000
order by d.department_id desc
limit 5;
#######################################################################################################
#PROBLEM 05
#Employees Without Project
#Вариант №1
select e.employee_id, e.first_name from employees as e
# с лефт джойн ще покажа всички от емплоиис и така ще видя приджект ид, които са нулл:
left join employees_projects as ep
#using (employee_id)
on e.employee_id = ep.employee_id
where project_id is null
order by employee_id desc
limit 3;
#Вариант №2
select employee_id, first_name from employees
where employee_id not in (select employee_id from employees_projects)
order by employee_id desc
limit 3;
#######################################################################################################
#PROBLEM 06
#Employees Hired After
select e.first_name, e.last_name, e.hire_date, d.name as dept_name  from employees as e
left join departments as d
on e.department_id = d.department_id
-- where e.hire_date > '1999-01-01 00:00:00' and d.name in ('Sales', 'Finance')
where date(e.hire_date) > '1999-01-01' and d.name in ('Sales', 'Finance')
order by e.hire_date asc;
#######################################################################################################
#PROBLEM 07
#Employees with Project
select e.employee_id, e.first_name, p.name as project_name from employees as e
join employees_projects as ep
on e.employee_id = ep.employee_id
join projects as p
on ep.project_id = p.project_id
# => when endDate is null => project is not finnished:
where date(p.start_date) > '2002-08-13' and date(p.end_date) is null
order by e.first_name asc, p.name asc
limit 5;
#######################################################################################################
#PROBLEM 08
#Employee 24
select e.employee_id, e.first_name, if( year(p.start_date) > 2004, NULL, p.name) as project_name 
from employees as e
join employees_projects as ep
on e.employee_id = ep.employee_id
join projects as p
on p.project_id = ep.project_id
where e.employee_id = 24
order by `project_name` asc;
#######################################################################################################
#PROBLEM 09
#Employee Manager
select e.employee_id, e.first_name, m.employee_id, m.first_name as manager_name
from employees as e
join employees as m
on m.employee_id = e.manager_id
where m.employee_id in (3,7)
order by e.first_name asc;
#######################################################################################################
#PROBLEM 10
select e.employee_id, 
concat_ws(' ', e.first_name, e.last_name) as employee_name,
concat_ws(' ', m.first_name, m.last_name) as manager_name, d.name as department_name
from employees as e
join employees as m
on e.manager_id = m.employee_id
join departments as d
on d.department_id = e.department_id
order by employee_id
limit 5;
#######################################################################################################
#PROBLEM 11
select avg(salary) as min_average_salary from employees
group by department_id
order by min_average_salary
limit 1;
#######################################################################################################
use geography;
#######################################################################################################
#PROBLEM 12
select c.country_code, m.mountain_range, p.peak_name, p.elevation from countries as c
join mountains_countries as mc
using (country_code)
join mountains as m
on m.id = mc.mountain_id
join peaks as p
on p.mountain_id = m.id
where c.country_name = 'Bulgaria' and p.elevation > 2835
order by p.elevation desc;
#######################################################################################################
#PROBLEM 13
select c.country_code, count(m.mountain_range) as mountain_range from countries as c
join mountains_countries as mc on c.country_code = mc.country_code
join mountains as m on m.id = mc.mountain_id
where c.country_code in ('BG', 'RU', 'US')
group by c.country_code
order by mountain_range desc;
#######################################################################################################
#PROBLEM 14
select c.country_name, r.river_name from countries as c
left join countries_rivers as cr on c.country_code = cr.country_code
left join rivers as r on r.id = cr.river_id
where c.continent_code = 'AF'
order by c.country_name asc
limit 5;
#######################################################################################################
#PROBLEM 15
SELECT 
    c.continent_code,
    c.country_code,
    COUNT(c.country_name) AS currency_usage
FROM countries AS c
GROUP BY c.continent_code , currency_code

HAVING currency_usage = (
SELECT 
        COUNT(country_code) AS coun
    FROM
        countries AS c1
    WHERE
        c1.continent_code = c.continent_code
    GROUP BY c1.currency_code
    ORDER BY coun DESC
    LIMIT 1
    ) AND currency_usage > 1
ORDER BY c.continent_code , c.currency_code;
#######################################################################################################
#PROBLEM 16
# със нестната заявка:
select count(*) as country_count from countries as c
where c.country_code not in (select country_code from mountains_countries );
#######################################################################################################
#PROBLEM 17
select c.country_name, max(p.elevation) as highest_peak_elevation, max(r.length) as longest_river_length from countries as c
left join mountains_countries as mc using (country_code)
left join mountains as m on m.id = mc.mountain_id
left join peaks as p on p.mountain_id = m.id
left join countries_rivers as cr using (country_code)
left join rivers as r on r.id = cr.river_id
group by c.country_name
order by highest_peak_elevation desc, longest_river_length desc, c.country_name
limit 5;

#минава и без лефт джойн щото имаме 5 реда, в които ще се сравнява. Ако имаше нъл в тях => трябваше с лефт:
SELECT 
    c.country_name,
    MAX(p.elevation) AS highest_peak_elevation,
    MAX(r.length) AS longest_river_length
FROM
    countries AS c
        JOIN
    mountains_countries AS mc USING (country_code)
        JOIN
    mountains AS m ON m.id = mc.mountain_id
        JOIN
    peaks AS p ON p.mountain_id = m.id
        JOIN
    countries_rivers AS cr USING (country_code)
        JOIN
    rivers AS r ON r.id = cr.river_id
GROUP BY c.country_name
ORDER BY highest_peak_elevation DESC , longest_river_length DESC , c.country_name
LIMIT 5;
#######################################################################################################


