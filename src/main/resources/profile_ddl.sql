create database profiles IF NOT EXIST;
CREATE USER ${db.user}@'localhost' IDENTIFIED BY '${db.password}';
CREATE USER ${db.user}@'%' IDENTIFIED BY '${db.password}';
ALTER USER '${db.user}'@'localhost' IDENTIFIED WITH mysql_native_password BY '${db.password}';
ALTER USER '${db.user}'@'%' IDENTIFIED WITH mysql_native_password BY '${db.password}';
GRANT ALL PRIVILEGES ON profiles.* to ${db.user}@'localhost';
GRANT ALL PRIVILEGES ON profiles.* to ${db.user}@'%';