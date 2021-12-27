ALTER TABLE `users`
drop primary key,
ADD CONSTRAINT pk_id
PRIMARY KEY (`id`);

ALTER TABLE `users` 
CHANGE COLUMN `username` 
`username` VARCHAR(30) NOT NULL UNIQUE;