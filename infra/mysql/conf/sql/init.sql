-- TODO : DB schema 생성 후 DDL 추가
create user IF NOT EXISTS 'test'@'%' identified by 'test';
CREATE DATABASE IF NOT EXISTS moyeomoyeo;
grant all privileges on moyeomoyeo.* to 'test'@'%';
flush privileges;