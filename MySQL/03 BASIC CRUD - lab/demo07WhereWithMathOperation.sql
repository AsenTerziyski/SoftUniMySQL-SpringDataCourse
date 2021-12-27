SELECT 
    *
FROM
    `employees`
WHERE
    `id` > 2 + 1;

SELECT 
    *
FROM
    `employees`
WHERE
    `id` > 2 / 3 + 1 * 2;

SELECT 
    *
FROM
    `employees`
WHERE
    `id` >= 1 * 2
        AND (`salary` BETWEEN 1000 AND 1900);

SELECT 
    *
FROM
    `employees`
ORDER BY `id` DESC;

SELECT 
    CONCAT(`first_name`, last_name) AS fullName
FROM
    `employees`
WHERE
    `id` != 3;