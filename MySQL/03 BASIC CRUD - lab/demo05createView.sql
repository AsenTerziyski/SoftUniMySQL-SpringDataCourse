create view `proba_view` as 
SELECT CONCAT_WS(' + ', `first_name`, `last_name`) AS FULL_NAME,
    `job_title` AS `job_title`,
    `id` AS No FROM
    `employees`;
    
    select * from `proba_view`;