CREATE DATABASE IF NOT EXISTS azuredemo CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE azuredemo;


CREATE TABLE IF NOT EXISTS users (
id INT AUTO_INCREMENT PRIMARY KEY,
username VARCHAR(255) NOT NULL UNIQUE,
password_hash VARCHAR(512) NOT NULL,
role VARCHAR(64) NOT NULL
);


-- Pre-seed admin: password is 'AdminPass123!' using PBKDF2-with-HmacSHA256 (salted)
-- We'll provide the hash created by the Java util; developer must generate and paste it here OR run the included SetupServlet that seeds admin using PasswordUtil.


-- Example placeholder (replace with your generated hash):
INSERT INTO users (username, password_hash, role) VALUES
('admin', '<REPLACE_WITH_HASH>', 'ADMIN');