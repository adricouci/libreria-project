drop user 'libreria'@'localhost';
create user 'libreria'@'localhost' identified by 'libreria';
grant all privileges on libreriadb.* to 'libreria'@'localhost';
flush privileges;