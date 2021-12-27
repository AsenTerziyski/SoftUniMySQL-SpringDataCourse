-- •	id – unique number for every person there will be no more than 231-1people. (Auto incremented)
-- •	name – full name of the person will be no more than 200 Unicode characters. (Not null)
-- •	picture – image with size up to 2 MB. (Allow nulls)
-- •	height –  In meters. Real number precise up to 2 digits after floating point. (Allow nulls)
-- •	weight –  In kilograms. Real number precise up to 2 digits after floating point. (Allow nulls)
-- •	gender – Possible states are m or f. (Not null)
-- •	birthdate – (Not null)
-- •	biography – detailed biography of the person it can contain max allowed Unicode characters. (Allow nulls)

-- ТОВА ОТИВА В ДЖЪДЖ: --

/*
KOMENTAR
*/

# ТОВА ОТИВА В ДЖЪДЖ:

CREATE TABLE `people` (
	`id` INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(200) NOT NULL,
    `picture` BLOB,
    `height` FLOAT(5, 2),	
    `weght` FLOAT(5, 2),
    `gender`CHAR(1) NOT NULL DEFAULT 'M',
    `birthdate` DATE NOT NULL,
    `biography` TEXT
);

INSERT INTO `people`
VALUES 
(1, 'Name1', 'picture1', 1.2, 1.21, 'm', '2001-01-10', 'rereererer' ),
(2, 'Name2', 'picture2', 1.2, 1.22, 'f', '2002-01-10', 'rereererer' ),
(3, 'Name3', 'picture3', 1.2, 1.23, 'm', '2000-01-10', 'rereererer' ),
(4, 'Name4', 'picture4', 1.2, 1.24, 'm', '2000-01-10', 'rereererer' ),
(5, 'Name5', 'picture5', 1.2, 1.25, 'f', '2000-01-10', 'rereererer' );








