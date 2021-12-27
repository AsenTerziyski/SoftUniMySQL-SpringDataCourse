-- create database `car_rental`;
-- use `car_rental`;

-- id, category, daily_rate, weekly_rate, monthly_rate, weekend_rate
create table `categories`(
	`id` int primary key auto_increment,
    `daily_rate` int not null,
    `weekly_rate` int not null,
    `monthly_rate` int not null,
    `weekend_rate` int not null
);

-- id, plate_number, make, model, car_year, category_id, doors, picture, car_condition, available 
create table `cars` (

	`id` int primary key auto_increment,
    `plate_number` varchar(30) not null unique,
    `make` varchar(30) not null,
    `model` varchar(30) not null,
    `car_year` year,
    `category_id` int,
    `doors` int not null,
    `picture` blob,
    `car_condition` varchar(30) default 'good',
    `avaible` boolean default true,
    
    constraint fk_cars_categories
    foreign key (`category_id`) references `cars` (`id`)
);

-- employees (id, first_name, last_name, title, notes)
create table `employees` (
	`id` int primary key auto_increment,
    `first_name` varchar(30) not null,
    `last_name` varchar(30) not null,
    `title` varchar(30) not null,
    `notes` text
);

-- customers (id, driver_licence_number, full_name, address, city, zip_code, notes)
create table `customers` (


);


