---drop database miklo_benjamin_eplanner;

create database if not exists miklo_benjamin_eplanner;
use miklo_benjamin_eplanner;

create table if not exists users(
    id int(11) not null AUTO_INCREMENT,
    username varchar(32) not null,
    email varchar(255) not null,
    crdate Date not null,
    password varchar(32) not null,
    firstname varchar(255),
    lastname varchar(255),
    primary key (id)
);

create table if not exists events(
    id int(11) not null AUTO_INCREMENT,
    username varchar(32) not null,
    ename varchar(255) not null,
    edesc text,
    edate Date not null,
    important bit not null,
    notified bit not null,
    primary key (id)
);

create table if not exists gevents(
    id int(11) not null AUTO_INCREMENT,
    username varchar(255) not null,
    ename varchar(255) not null,
    edesc text,
    edate Date not null,
    important bit not null,
    primary key (id)
);

create table if not exists gusers(
    id int(11) not null AUTO_INCREMENT,
    gname varchar(32),
    primary key(id)
);
