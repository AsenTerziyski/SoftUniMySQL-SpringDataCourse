-- ALTER TABLE `towns`
-- CHANGE COLUMN `town_id` `id` INT NOT NULL AUTO_INCREMENT;

-- това е задача 02-- 
ALTER TABLE `minions`
ADD COLUMN `town_id` int,
ADD CONSTRAINT fk_minions_towns
FOREIGN KEY (`town_id`)
REFERENCES `towns` (`id`);