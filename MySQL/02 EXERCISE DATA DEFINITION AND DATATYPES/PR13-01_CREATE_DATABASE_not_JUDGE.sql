-- Now create bigger database called soft_uni. You will use database in the future tasks. It should hold information about
--     • towns (id, name)
--     • addresses (id, address_text, town_id)
--     • departments (id, name)
--     • employees (id, first_name, middle_name, last_name, job_title, department_id, hire_date, salary, address_id)

CREATE DATABASE `soft_uni`;

USE `soft_uni`;

CREATE TABLE `towns` (
`id` INT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(30) NOT NULL
);

CREATE TABLE `addresses` (
`id` INT PRIMARY KEY AUTO_INCREMENT,
`address_text` VARCHAR(100) NOT NULL,
`town_id` INT NOT NULL,
CONSTRAINT fk_addresses_towns
FOREIGN KEY (`town_id`) REFERENCES `towns` (`id`)
);

CREATE TABLE `departments` (
`id` INT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(20) not null
);

CREATE TABLE `employees` (
`id` INT PRIMARY KEY AUTO_INCREMENT,
`first_name` VARCHAR(30) NOT NULL,
`middle_name` VARCHAR(30) NOT NULL,
`last_name` VARCHAR(30) NOT NULL,
`job_title` VARCHAR(20),
`salary` DECIMAL(10,2),
`departmant_id` INT,
`hire_date` DATE,
`address_id` INT,

CONSTRAINT fk_employees_departments
FOREIGN KEY (`departmant_id`) REFERENCES `departments`(`id`),

CONSTRAINT fk_employees_address
FOREIGN KEY (`address_id`) REFERENCES `addresses` (`id`)
);


# това отива в Джъдж:

INSERT INTO `towns` (`name`)
VALUES
('Sofia'),
('Plovdiv'),
('Varna'),
('Burgas');

# Engineering, Sales, Marketing, Software Development, Quality Assurance
INSERT INTO `departments` (`name`)
VALUES
('Engineering'),
('Sales'),
('Marketing'),
('Software Development'),
('Quality Assurance');

INSERT INTO `employees` (`id`, `first_name`, `middle_name`, `last_name`, `job_title`,`salary`,`departmant_id`,`hire_date`,`address_id`)
VALUES 
(1, 'Ivan', 'Ivanov', 'Ivanov', '.NET Developer', 3500.00, 4, '2013-02-01', null),
(2, 'Petar', 'Petrov', 'Petrov', 'Senior Engineer', 4000.00, 1, '2004-03-02', null),
(3, 'Maria', 'Petrova', 'Ivanova', 'Intern', 525.25, 5, '2016-08-28', null),
(4, 'Georg', 'Terziev', 'Ivanov', 'CEO', 3000.00, 2, '2007-12-09', null),
(5, 'Peter', 'Pan', 'Pan', 'Intern', 599.88, 3, '2016-08-28', null);





