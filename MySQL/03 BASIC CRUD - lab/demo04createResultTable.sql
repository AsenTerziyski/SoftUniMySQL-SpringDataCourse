CREATE TABLE `result_table` AS (SELECT CONCAT_WS(' + ', `first_name`, `last_name`) AS FULL_NAME,
    `job_title` AS `job_title`,
    `id` AS No FROM
    `employees`);
        
--     alter table `result_table`
--     add column `proba` varchar(100);
--     
--     alter table `result_table`
--     drop column `proba`;

--     drop table `result_table`; 