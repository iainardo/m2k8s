create database IF NOT EXISTS profiles ;

USE profiles;

CREATE TABLE IF NOT EXISTS `emp_profile` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(30) NOT NULL,
  `image_file_content_type` varchar(255) DEFAULT NULL,
  `image_file_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(30) NOT NULL,
  `password` varchar(25) NOT NULL,
  `username` varchar(16) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

CREATE USER empuser@`localhost` IDENTIFIED BY `password`;
CREATE USER empuser@`%` IDENTIFIED BY `password`;
ALTER USER `empuser`@`localhost` IDENTIFIED WITH mysql_native_password BY `password`;
ALTER USER `empuser`@`%` IDENTIFIED WITH mysql_native_password BY `password`;
GRANT ALL PRIVILEGES ON profiles.* to empuser@`localhost`;
GRANT ALL PRIVILEGES ON profiles.* to empuser@`%`;

FLUSH privileges;