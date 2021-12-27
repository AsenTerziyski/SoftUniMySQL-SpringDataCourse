-- select * from employees
-- order by salary desc
-- limit 1;

create view `v_top_paid_employee`as (
select * from `employees`
order by `salary` desc
limit 1
);

select * from `v_top_paid_employee`;