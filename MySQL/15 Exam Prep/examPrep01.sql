# PRAVYA SI DATABASA ZA IZPITA. MOGA DA YA KRASTYA PROIZVOLNO:
create database fsd;
use fsd;

# SECTION 1

# PROBLEM 01 : Table Design.
create table countries (
id int primary key auto_increment,
name varchar(45) not null
);

create table towns (
id int primary key auto_increment,
name varchar(45) not null,
country_id int not null,

constraint fk_towns_countries
foreign key (country_id) references countries(id)
);

create table stadiums (
id int primary key auto_increment,
name varchar(45) not null,
capacity int not null, 
town_id int null,

constraint fk_stadium_towns
foreign key (town_id) references towns (id)
);

create table teams (
id int primary key auto_increment,
name varchar(45) not null,
established date not null,
fan_base bigint not null default 0,
stadium_id int not null,

constraint fk_teams_stadiums
foreign key (stadium_id) references stadiums(id)
);

create table skills_data (
	id int primary key auto_increment,
    dribbling int default 0,
	pace int default 0,
	passing int default 0,
	shooting int default 0,
	speed int default 0,
	strength int default 0
);

create table players (
	id int primary key auto_increment,
	first_name varchar(10) not null,
	last_name varchar(20) not null,
	age int not null default 0,
	position char(1) not null,
	salary decimal(10,2) not null default 0,
	hire_date datetime,
	skills_data_id int not null,
	team_id int,
    constraint fk_players_skills
    foreign key (skills_data_id) references skills_data(id),
    constraint fk_players_teams
    foreign key (team_id) references teams(id)
);

create table coaches (
	id int primary key auto_increment,
	first_name varchar(10) not null,
	last_name varchar(20) not null,
	salary decimal(10,2) not null default 0,
	coach_level int not null default 0
);

create table players_coaches (
player_id int,
coach_id int,

constraint pk_players_coaches
primary key (player_id, coach_id),

constraint fk_players_coaches_players
foreign key (player_id) references players(id),

constraint fk_players_coaches_coaches
foreign key (coach_id) references coaches(id)
);

# SECTION 2

# PROBLEM 02 : Insert.
insert into coaches (first_name, last_name, salary, coach_level)
# Във инсърт можем да подадем директно резултата от селекта:
select first_name, last_name, salary * 2, char_length(first_name) from players
where age>=45;

# PROBLEM 03 : Update.
update coaches
set coach_level = coach_level + 1
where left(first_name, 1) = 'A' and id in (select coach_id from players_coaches);

# PROBLEM 04 : Delete.
delete from players
where age>=45;

# SECTION 3

#PROBLEM 05 : Players.
select p.first_name, p.age, p.salary from players as p
order by p.salary desc;

#PROBLEM 06 : Young offense players without contract.
select p.id, concat_ws(' ', p.first_name, p.last_name) as full_name, p.age, p.position, p.hire_date from players as p
join skills_data as sd
on sd.id = p.skills_data_id
where(p.age < 23 and p.position = 'A' and p.hire_date is null and sd.strength >50)
order by p.salary asc, p.age;

#PROBLEM 07 : Detail info for all teams
select t.name as team_name, t.established, t.fan_base, count(p.id) as players_count from teams as t
# all of the teams => left join (в случая):
left join players as p
on t.id = p.team_id
# групирането е по лефт тейбъл:
group by t.id
order by players_count desc, t.fan_base desc;	

#PROBLEM 08 : The fastest player by towns
select max(sd.speed) as max_speed, t.name as town_name from towns as t
left join stadiums as s
on  t.id = s.town_id
left join teams as tms
on s.id = tms.stadium_id
left join players as p
on  tms.id = p.team_id
left join skills_data as sd
on p.skills_data_id = sd.id
where tms.name != 'Devify'
# grupiraneto mozhe da e i po t.id:
group by t.name
order by  max_speed desc, t.name;

#PROBLEM 09 : Total salaries and players by country.
select c.name, count(p.id) as total_count_of_players, sum(p.salary) as total_sum_of_salaries 
from countries as c

left join towns as t
on c.id = t.country_id

left join stadiums as s
on t.id = s.town_id

left join teams as tms
on s.id = tms.stadium_id

left join players as p
on tms.id = p.team_id
-- where c.name = 'Russia'
group by c.name
order by total_count_of_players desc, c.name;

# SECTION 4
# nyama trigeri i transactsii:
#PROBLEM 10 : Find all players that play on stadium
# functions
# първо hardcore заявката:
select count(p.id) as count from stadiums as s
left join teams as t
on t.stadium_id = s.id
left join players as p
on p.team_id = t.id
where s.name = 'Jaxworks';

delimiter $$
create function udf_stadium_players_count (stadium_name VARCHAR(30))
returns int
# deterministic oznachava che pri vhod edni i sashti parametri, vrashta edni i sashti resultati:
deterministic
begin
# return (tuk zayavkata):
return(

select count(p.id) as count from stadiums as s
left join teams as t
on t.stadium_id = s.id

left join players as p
on p.team_id = t.id
where s.name = stadium_name

);
end$$
delimiter ;

select udf_stadium_players_count('Jaxworks') as count;
select udf_stadium_players_count('Linklinks') as count;
select udf_stadium_players_count('Yadel') as count;

#PROBLEM 11 : Find good playmaker by teams.
# procedures

select concat_ws(' ', p.first_name, p.last_name) as full_name, p.age, p.salary, sd.dribbling, sd.speed, t.name from teams as t
join players as p
on t.id = p.team_id
join skills_data as sd
on p.skills_data_id = sd.id
where sd.dribbling > 20 and t.name = 'Skyble'
order by sd.speed desc
limit 1;

delimiter $$
create procedure udp_find_playmaker (min_dribble_points int, team_name varchar(45))
begin
	select concat_ws(' ', p.first_name, p.last_name) as full_name, p.age, p.salary, sd.dribbling, sd.speed, t.name from teams as t
	join players as p
	on t.id = p.team_id
	join skills_data as sd
	on p.skills_data_id = sd.id
	where sd.dribbling > min_dribble_points and t.name = team_name
	order by sd.speed desc
	limit 1;
end$$
delimiter ;

call udp_find_playmaker(20, 'Skyble');
