## 1. MariaDB 安装与配置
```shell
:~$ sudo apt install mariadb-server
```
默认情况下，新安装的 mariadb 的密码为空，在shell终端直接输入 mysql 就能登陆数据库。
如果是刚安装第一次使用，请使用 mysql_secure_installation 命令初始化。

## 2. 登陆数据库修改密码。

更新 mysql 库中 user 表的字段：
```sql
MariaDB [(none)]> use mysql;  
MariaDB [mysql]> UPDATE user SET password=password('newpassword') WHERE user='root';
```
或者
```sql
MariaDB [mysql]> ALTER USER 'root'@'localhost' IDENTIFIED BY 'newpassword';
MariaDB [mysql]> flush privileges;  
MariaDB [mysql]> exit;
```
或者，使用 set 指令设置root密码：
```sql
MariaDB [(none)]> SET password for 'root'@'localhost' = password('newpassword');  
MariaDB [(none)]> exit; 
```


## 3. 新建一个my用户并且授权全部操作权限

```sql
MariaDB [(none)]> grant all privileges on *.* to my@localhost identified by '123456';
Query OK, 0 rows affected (0.00 sec)

MariaDB [(none)]> select user from mysql.user;
```

dbuser@1q2w3e@4R
