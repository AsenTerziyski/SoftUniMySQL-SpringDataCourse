	create database `movies`;
    use `movies`;

create table `directors` (
`id` int primary key auto_increment,
`director_name` varchar(30) not null,
`notes` text
);

create table `genres` (
`id` int primary key auto_increment,
`genre_name` varchar(30) not null,
`notes` text
);

create table `categories` (
`id` int primary key auto_increment,
`category_name` varchar(30) not null,
`notes` text
);

create table `movies` (
`id` int primary key auto_increment,
`title` varchar(1000) not null,
`director_id` int,
`copyright` varchar(30),
`year` date,
`length` time,
`genre_id` int,
`category_id` int,
`rating` int,
`notes` text default null,

constraint fk_movies_directors
foreign key (`director_id`) references `directors` (`id`),

constraint fk_movies_genres
foreign key (`genre_id`) references `genres` (`id`),

constraint fk_movies_category
foreign key (`category_id`) references `categories` (`id`)

);

insert into `directors` (`director_name`, `notes`)
values
('d1', 'd1 - notes'),
('d2', 'd2 - notes'),
('d3', 'd3 - notes'),
('d4', 'd4 - notes'),
('d5', 'd5 - notes');

insert into `genres` (`genre_name`, `notes`) 
values
('g1', 'g1-notes-notes'),
('g2', 'g2-notes-notes'),
('g3', 'g3-notes-notes'),
('g4', 'g4-notes-notes'),
('g5', 'g5-notes-notes');
 
insert `categories` (`category_name`, `notes`)
values
('c1', 'c1notes....'),
('c2', 'c2notes....'),
('c3', 'c3notes....'),
('c4', 'c4notes....'),
('c5', 'c5notes....');

insert into `movies` (`title`, `copyright`, `year`, `length`, `rating`, `director_id`, `genre_id`, `notes`)
values
('t1', 'copyright1', '2001-01-12', 100, 101, 1, 1, 'notesNotesNotes.....1'),
('t2', 'copyright10', '2001-01-10', 100, 101, 3, 5, 'notesNotesNotes....2'),
('t3', 'copyright100', '2001-01-02', 100, 101, 5, 3, 'notesNotesNotes...3'),
('t4', 'copyright1000', '2001-01-10', 100, 101, 2, 4, 'notesNotesNotes..4'),
('t5', 'copyright10000', '2001-01-22', 100, 101, 4, 2, 'notesNotesNote..5');






    