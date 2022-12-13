CREATE DATABASE server DEFAULT CHARACTER SET utf8;
USE server;

CREATE TABLE operlog (
     id BIGINT auto_increment NOT NULL PRIMARY KEY,
     login_name varchar(100) NULL,
     user_name varchar(100) NULL,
     oper_time datetime NULL,
     address varchar(100) NULL,
     category int NULL,
     description text NULL,
     result int NULL,
     failure_reason text NULL
);

CREATE TABLE markdown_file (
    id BIGINT auto_increment NOT NULL PRIMARY KEY,
    title varchar(256) NULL,
    date datetime NULL,
    source_file_path varchar(256) NULL,
    md_file text NULL,
    html_file text NULL,
    category INT NULL
);

CREATE TABLE guest_visit_info (
    id BIGINT auto_increment NOT NULL PRIMARY KEY,
    guest_name varchar(256) NULL,
    phone_number varchar(256) NULL,
    commit_time datetime NULL,
    start_time datetime NULL,
    end_time datetime NULL,
    uuid varchar(64) NULL,
    check_code varchar(64) NULL,
    validation boolean NOT NULL,
    notes text NULL
);

CREATE TABLE guest (
    id BIGINT auto_increment NOT NULL PRIMARY KEY,
    name varchar(256) NULL,
    phone_number varchar(256) NULL,
    statistics INT NOT NULL
);

