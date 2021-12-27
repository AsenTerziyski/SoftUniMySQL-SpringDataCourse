#demoes 
#string functions
#demo substring:
select substring('testTesttest', 5);
select substring('testTesttest',5, 4);
##може и substr:
select substr('testTesttest' from 5 for 4);
select first_name, substr(first_name, 1, 2) as substracted 
from employees;
select title, substr(title, 1, 3) as substracted_title 
from books
where substr(title, 1, 3) = 'The'
order by title desc;
select title, replace(title, 'The', 'xxx') as censured 
from books
order by censured desc;
select replace(title, 'Harry Potter', 'XXXXXXXX') as censured
from books
where substr(title, 1, 12 ) = 'Harry Potter'
order by censured;
select ltrim('   Test');
select rtrim('Test     ');
select trim('  Test   ');
#count number of characters
select char_length('Test');
select char_length('Тест');
#get number of used bytes (double for Unicode)
select length('Test');
select length('Тест');
#LEFT & RIGHT – get characters from beginning or end of string
select left(title, 3) as first_3_letters_from_title from books;
select right(title, 3) as last_3_letters_from_title from books;
#reverse
select title, reverse(title) as reversed_title from books
order by reversed_title;
#lower and upper:
select title, upper(title) as title_as_upper_letters from books;
#repeat
-- select title, upper(repeat(title, 2)) as repeated_title from books;

#insert
-- select insert ('Test', 2, 1, 'xxx');
-- select insert (title, 2, 1, 'o!o') from books;
#math functions
select pi();
select abs(-100);
select cost, round(sum(sqrt(cost))) from books;
select cost, round(sqrt(cost), 2) as modified from books
group by id
order by id desc;
select pow(3,3);
select cost, pow(cost, 10) as pow_cost from books
group by id
order by id;
create table test_teble (
select cost, pow(cost, 10) as pow_cost from books
group by id
order by id
);
select cost, round(sqrt(pow_cost),2) from test_teble;
alter table test_teble
rename to test_table_renamed;
select cost, floor(cost) as floored_cost from books;
select cost, ceil(cost) as ceiled_cost from books;
update test_table_renamed
set pow_cost = cost * 1.2;
alter table test_table_renamed
rename column pow_cost to cost_with_VAT;
update test_table_renamed
set cost_with_VAT = round(cost, 0);
select left(rand()*100,1);
select id, title from books
where id = left(rand()*100,1)
order by id;
select sign(-10);
#date functions
SELECT 
    id, title, year_of_release,
    YEAR(year_of_release) AS extracted_YEAR,
    DAY(year_of_release) AS extracted_DAY,
    HOUR(year_of_release) AS extracted_hour
FROM
    books
ORDER BY id DESC , extracted_YEAR;

create table test_table (
SELECT  id, title, YEAR(year_of_release) AS extracted_YEAR from books
);

select id, concat_ws(' ', first_name, middle_name, last_name, 'lived: ') as author, timestampdiff(year, born, died) as `lived`
from authors
where timestampdiff(year, born, died) is not null
order by id;
select now();

select id, first_name, last_name, timestampdiff(year, born, '2021-04-04') as days_from_authors_birth 
from authors
order by id;

SELECT `id`, `first_name`, `last_name`,
       TIMESTAMPDIFF(year, `born`, '2017-05-31') AS 'years_since_authors_birth'
FROM `authors`;

SELECT `id`, `first_name`, `last_name`,
       TIMESTAMPDIFF(year, `born`, '2017-05-31') AS 'years_since_authors_birth'
FROM `authors`;
  
  #demo Wildcards – Examples
select first_name from authors
where first_name like 'a%';
  
select first_name from authors
where first_name like '%ka';
  
select first_name from authors
where first_name like '_r%';
  
select first_name from authors
where first_name like '%k_';

select first_name from authors
where first_name like 'A%a';

select title from books
where title like 'Harry Potter%';
  
#exercising lab problems

#problem 01
select title from books
where title like 'The%';
#може и по този начин:
select title from books
where substr(title, 1, 3) = 'The'
order by title;
#************************************
#problem 02
select 
replace(title, 'The', 'xxx')
from books
where title like 'The%'
order by id;
#************************************
#create table censured_titeles
create table censured_titles(
select 
replace(title, 'The', 'xxx') as censured_title, 
id as book_No
from books
where title like 'The%'
order by id
);
#************************************
#problem 03
select round(sum(cost), 2) from books;
#може и така:
select format(sum(cost), 2) from books;
#************************************
#problem 04
select concat_ws(' ', first_name, middle_name, last_name) as 'Full Name',
timestampdiff(day, born, died)
from authors;
#************************************
#problem 05
select title from books
where title like '%Harry Potter%';
#************************************