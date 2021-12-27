alter table `users`
drop primary key,
add constraint pk_users
primary key (`id`);

alter table `users`
change column `username` `username` varchar(30) not null unique;