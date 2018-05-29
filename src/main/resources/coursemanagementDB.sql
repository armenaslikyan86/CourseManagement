# create database coursemanagementDB;

use coursemanagementDB;


create table announcement (
  id int auto_increment primary key not null ,
  title nvarchar(255) not null ,
  description nvarchar(255) not null
) engine = InnoDB;

create table course (
  id int auto_increment primary key not null ,
  name varchar(255) not null ,
  duration int not null ,
  description TEXT not null ,
  price double not null
)engine = InnoDB;

create table user (
  id int auto_increment primary key not null ,
  title varchar(255) not null ,
  firstName varchar(255) not null ,
  lastName varchar(255) not null ,
  age int not null ,
  email varchar(255) not null unique ,
  description text default null ,
  phoneNumber varchar(255) not null ,
  role int not null ,
  login varchar(255) not null unique ,
  passHash varchar(255) not null
)engine = InnoDB;

create table score (
  id int auto_increment primary key not null ,
  attendance int not null ,
  knowledge int not null
)


