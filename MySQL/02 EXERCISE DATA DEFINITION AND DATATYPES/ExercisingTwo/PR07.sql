create table `users` (
`id` int primary key auto_increment unique,
`username` varchar(30) unique,
`password` varchar(26),
`picture` blob,
`last_login_time` timestamp default current_timestamp,
`is_deleted` boolean
);

insert into `users`
values
(1, 'U1a', 'pass1', 'picture1', '2000-05-05 05:05:05', true),
(2, 'U2b', 'pass1', 'picture1', '2000-05-05 05:05:05', true),
(3, 'U3c', 'pass1', 'picture1', '2000-05-05 05:05:05', true),
(4, 'U4d', 'pass1', 'picture1', '2000-05-05 05:05:05', true),
(5, 'U5e', 'pass1', 'picture1', '2000-05-05 05:05:05', true);