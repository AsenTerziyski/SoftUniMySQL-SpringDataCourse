CREATE VIEW `my_view` AS
    (SELECT 
        `id` AS ID,
        CONCAT(`first_name` + ' ' + `last_name`) AS full_name,
        `salary` AS ZAPLATA
    FROM
        `employees`);
        