#problem 01

#първо си пиша селекта:
select e.first_name, e.last_name from employees as e
where e.salary > 35000
order by e.first_name, e.last_name, e.employee_id asc;
#parvo smenyam delimitera:
delimiter $$
create procedure usp_get_employees_salary_above_35000 ()
begin
select e.first_name, e.last_name from employees as e
where e.salary > 35000
order by e.first_name, e.last_name, e.employee_id asc;
end$$
#opravyam delimitara:
delimiter ;
#izvikvam prcedurata:
call usp_get_employees_salary_above_35000 ();

#problem 02
select e.first_name, e.last_name from employees as e
where e.salary >= 4500
order by e.first_name, e.last_name, e.employee_id asc;

delimiter $$
create procedure usp_get_employees_salary_above (decimal_number decimal(19,4))
begin
select e.first_name, e.last_name from employees as e
where e.salary >= decimal_number
order by e.first_name, e.last_name, e.employee_id asc;
end $$
delimiter ;

#problem 03

select t.name as town_name from towns as t
where lower(t.name) like concat('b','%')
order by t.name;

delimiter $$
create procedure usp_get_towns_starting_with (begin_string varchar(50))
begin
select t.name as town_name from towns as t
where lower(t.name) like concat(begin_string, '%')
order by t.name;
end $$
delimiter ;

call usp_get_towns_starting_with ('b');

#problem 04
select e.first_name, e.last_name, t.name from employees as e
join addresses as a
on a.address_id = e.address_id
join towns as t
on t.town_id = a.town_id
where t.name = 'Sofia'
order by e.first_name, e.last_name, e.employee_id asc;

delimiter $$
create procedure usp_get_employees_from_town (town_name varchar(50))
begin
select e.first_name, e.last_name from employees as e
join addresses as a
on a.address_id = e.address_id
join towns as t
on t.town_id = a.town_id
where t.name = town_name
order by e.first_name, e.last_name, e.employee_id asc;
end$$
delimiter ;

call usp_get_employees_from_town ('Sofia');

#problem 05

delimiter $$
create function ufn_get_salary_level (salary decimal(19,4))
#parvo return tipa:
returns varchar(50)
#posle slagam deterministic 
deterministic
begin
declare salary_level varchar(50);

if (salary < 30000) then set salary_level := 'Low';
elseif (salary <= 50000) then set salary_level := 'Average';
else set salary_level := 'High';
end if;

return salary_level;

end$$
delimiter ;

select ufn_get_salary_level (13500.00);

-- select e.salary, 
-- (
-- case
-- when e.salary < 30000 then 'Low'
-- when e.salary <= 50000 then 'Average'
-- else 'High'
-- end
-- ) as salary_level
-- from employees as e;

#problem 06
select e.first_name, e.last_name from employees as e
where (select ufn_get_salary_level (e.salary) = 'High')
order by e.first_name desc, e.last_name desc;

delimiter $$
create function ufn_get_salary_level (salary decimal(19,4))
returns varchar(50)
deterministic
begin
declare salary_level varchar(50);

if (salary < 30000) then set salary_level := 'Low';
elseif (salary <= 50000) then set salary_level := 'Average';
else set salary_level := 'High';
end if;

return salary_level;
#kogato imam funkciya/ili procedura i funkciya ili procedura => na mezdinnya end slagam ; za delimiter:
end;

create procedure usp_get_employees_by_salary_level (salary_level varchar(50))
begin

select e.first_name, e.last_name from employees as e
where (select ufn_get_salary_level (e.salary) = salary_level)
order by e.first_name desc, e.last_name desc;
end $$
delimiter ;

call usp_get_employees_by_salary_level ('High');

#problem 07

delimiter $$
create function ufn_is_word_comprised(set_of_letters varchar(50), word varchar(50))
returns bit
deterministic
begin
return (select lower(word) regexp(concat('^[', set_of_letters, ']+$')));
end$$
delimiter ;
select ufn_is_word_comprised ('pppp', 'Guy');
select ufn_is_word_comprised ('oistmiahf', 'Sofia');
select ufn_is_word_comprised ('pppp', 'Guy');

#problem 08
select concat_ws(' ', a.first_name, a.last_name) as full_name from account_holders as a
order by full_name, id asc;

delimiter $$
create procedure usp_get_holders_full_name()
begin
select concat_ws(' ', a.first_name, a.last_name) as full_name from account_holders as a
order by full_name, id asc;
end$$
delimiter ;

call usp_get_holders_full_name();

#problem 09
-- select ah.first_name, ah.last_name,sum(a.balance)  from account_holders as ah
-- join accounts as a
-- on a.account_holder_id = ah.id
-- group by ah.id
-- having sum(a.balance)> 2000000
-- order by ah.id;

-- delimiter $$
-- create procedure usp_get_holders_with_balance_higher_than (numb decimal (20,20))
-- begin
-- SELECT 
--     *
-- FROM
--     account_holders AS ah
--         JOIN
--     (SELECT 
--         *
--     FROM
--         accounts
--     GROUP BY a.id
--     HAVING SUM(balance) > numb) AS a ON a.account_holders_id = ah.id
-- ORDER BY a.account_holder_id;
-- end$$
-- delimiter ;
-- call usp_get_holders_with_balance_higher_than (7000);

select ah.first_name, ah.last_name from account_holders as ah
join accounts as a
on ah.id = a.account_holder_id
group by ah.id
having sum(a.balance) > 7000;

delimiter $$
create procedure usp_get_holders_with_balance_higher_than (num decimal(20,7))
begin
select ah.first_name, ah.last_name from account_holders as ah
join accounts as a
on ah.id = a.account_holder_id
group by ah.id
having sum(a.balance) > num;
end$$
delimiter ;
call usp_get_holders_with_balance_higher_than (7000);

#problem 10
delimiter $$
create function ufn_calculate_future_value (sum decimal(19,4), interest decimal(19,4), years int)
returns decimal(19,4)
deterministic
begin
return sum * (pow((1+interest), years));
end $$
delimiter ;
select ufn_calculate_future_value (1000, 0.50, 5);

#problem 11
select a.id, ah.first_name, ah.last_name, a.balance as current_balance,
ufn_calculate_future_value (a.balance, 0.10, 5) as balance_in_5_years
from accounts as a
join account_holders as ah
on ah.id = a.account_holder_id;

delimiter $$
create procedure usp_calculate_future_value_for_account (id int, interest double)
begin
select a.id, ah.first_name, ah.last_name, a.balance as current_balance,
ufn_calculate_future_value (a.balance, interest, 5) as balance_in_5_years
from accounts as a
join account_holders as ah
on ah.id = a.account_holder_id
-- having a.id = id;
where a.id = id;
end$$
delimiter ;

call usp_calculate_future_value_for_account (1, 0.1);

#problem 12
delimiter $$
create procedure usp_deposit_money(acc_id int, money_amount decimal(19,4)) 
begin
start transaction;
	if (select count(*) from accounts where id = acc_id ) = 0 or (money_amount <=0)
	then rollback;
else
	update accounts
	set balance = balance + money_amount
	where id = acc_id;
	end if;
    commit;
end$$
delimiter ;

call usp_deposit_money (1,10);
call usp_deposit_money ( 2,100000);

#problem 13
delimiter $$
create procedure usp_withdraw_money (acc_id int, money_amount decimal(19,4))
begin
start transaction;
	if (select count(*) from accounts where id = acc_id) = 0
	or (money_amount <= 0) or 
    ((select balance from accounts where id = acc_id) <=  money_amount )
then rollback;
else
	update accounts
    set balance = balance - money_amount
    where id = acc_id;
end if;
end$$
delimiter ;

call usp_withdraw_money (1,90);
select balance from accounts where id = 1;

#problem14
delimiter $$
create procedure usp_transfer_money(from_account_id int, to_account_id int, amount decimal(19,4)) 
begin
start transaction;
	if (select count(*) from accounts where id = from_account_id) = 0 
    or (select count(*) from accounts where id = to_account_id) = 0
	or (amount <= 0)
    or ((select balance from accounts where id = from_account_id) <=  amount )
then rollback;
else
	update accounts
    set balance = balance - amount
    where id = from_account_id;
    
	update accounts
    set balance = balance + amount
    where id = to_account_id;
    
end if;
end$$
delimiter ;

call usp_transfer_money (1,2, 10);

#ostava za trigerite!


