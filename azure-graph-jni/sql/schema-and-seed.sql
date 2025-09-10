CREATE DATABASE IF NOT EXISTS azuredemo CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE azuredemo;


CREATE TABLE IF NOT EXISTS users (
id INT AUTO_INCREMENT PRIMARY KEY,
username VARCHAR(255) NOT NULL UNIQUE,
password_hash VARCHAR(512) NOT NULL,
role VARCHAR(64) NOT NULL
);

INSERT INTO users (username, password_hash, role) VALUES

('admin', '<REPLACE_WITH_HASH>', 'ADMIN');
