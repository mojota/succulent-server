1.service mysqld stop
2.vim /etc/my.cnf
[mysqld]
character_set_server=utf8
3.service mysqld start

create database succulent;
ALTER TABLE user auto_increment=1000000;