CREATE DATABASE server DEFAULT CHARACTER SET utf8;
USE server;

CREATE TABLE operlog (
     id BIGINT auto_increment NOT NULL,
     loginName varchar(100) NULL,
     userName varchar(100) NULL,
     operTime varchar(100) NULL,
     address varchar(100) NULL,
     category int NULL,
     description text NULL,
     result int NULL,
     failureReason text NULL,
     PRIMARY KEY (id)
);
