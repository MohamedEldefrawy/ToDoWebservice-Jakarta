create database if not exists todos;

use todos;



create table if not exists priorities
(
    id int primary key auto_increment,
    name varchar(50)
);

create table if not exists categories
(
    id int primary key auto_increment,
    name varchar(50)
);


create table if not exists items
(
    id int primary key auto_increment,
    title varchar(50),
    description text,
    start_date date,
    end_date date,
    priority_id int,
    category_id int,
    FOREIGN KEY (priority_id) REFERENCES priorities(id),
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

alter table items
add isFavourite bit default 0;

-- Insert initial categories and priorities
insert into categories values (1,'DEFAULT');
insert into categories values (2,'WORK');
insert into categories values (3,'HOBBY');
insert into categories values (4,'ROUTINE');

insert into priorities values (1,'HIGH');
insert into priorities values (2,'MEDIUM');
insert into priorities values (3,'LOW');

select * from items;