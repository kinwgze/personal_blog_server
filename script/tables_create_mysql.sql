CREATE DATABASE server DEFAULT CHARACTER SET utf8;
USE server;

CREATE TABLE TBL_OPERLOG (
     id BIGINT auto_increment NOT NULL,
     login_name varchar(100) NULL,
     user_name varchar(100) NULL,
     oper_time varchar(100) NULL,
     address varchar(100) NULL,
     category int NULL,
     description text NULL,
     result int NULL,
     failure_reason text NULL,
     PRIMARY KEY (id)
);

CREATE TABLE TBL_MARKDOWN_FILE (
    id BIGINT auto_increment NOT NULL,
    title varchar(256) NULL,
    date varchar(100) NULL,
    source_file_path varchar(256) NULL,
    md_file text NULL,
    html_file text NULL,
    category INT NULL,
    PRIMARY KEY (id)
);

