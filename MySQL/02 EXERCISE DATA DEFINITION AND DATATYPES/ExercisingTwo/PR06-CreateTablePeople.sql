-- create database `peopleDatabase`;

create table `people` (
`id` int primary key auto_increment unique,
`name` varchar(200) not null,
`picture` blob,
`height` float(5,2),
`weght` float(5, 2),
`gender` char(1) not null default 'm',
`birthdate` date not null,
`biography` text
);

insert into `people`
values
(1, 'NameA', 'picture1', 1.2, 1.21, 'M', '2001-01-10', 'xxxxxxxxxx'),
(2, 'NameB', 'picture1', 1.2, 1.21, 'F', '2001-01-10', 'rereererer'),
(3, 'NameC', 'picture1', 1.2, 1.21, 'M', '2001-01-10', 'xxxxxxxxxx'),
(4, 'NameD', 'picture1', 1.2, 1.21, 'F', '2001-01-10', 'rereererer'),
(5, 'NameE', 'picture1', 1.2, 1.21, 'M', '2001-01-10', 'xxxxxxxxxx');