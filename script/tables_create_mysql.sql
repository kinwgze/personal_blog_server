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

CREATE TABLE zeroMdFile (
    id BIGINT auto_increment NOT NULL,
    title varchar(256) NULL,
    date varchar(100) NULL,
    sourceFilePath varchar(256) NULL,
    htmlFilePath varchar(256) NULL,
    description text NULL,
    category INT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE dailyMdFile (
    id BIGINT auto_increment NOT NULL,
    title varchar(256) NULL,
    date varchar(100) NULL,
    sourceFilePath varchar(256) NULL,
    htmlFilePath varchar(256) NULL,
    description text NULL,
    category INT NULL,
    PRIMARY KEY (id)
);
