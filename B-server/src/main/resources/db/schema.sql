CREATE DATABASE IF NOT EXISTS demo_db;
USE demo_db;

DROP TABLE IF EXISTS user;
CREATE TABLE user (
      id BIGINT AUTO_INCREMENT PRIMARY KEY,
      username VARCHAR(50) NOT NULL,
      password VARCHAR(100) NOT NULL,
      email VARCHAR(100),
      age INT,
      create_time DATETIME,
      update_time DATETIME
);

insert into user (username, password, email, age, create_time, update_time)
values ('admin', 'w123456789', 'admin@example.com', 30, now(), now());
