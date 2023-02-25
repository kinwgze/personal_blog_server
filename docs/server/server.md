## Ubuntu开启root登录

>1.切换到 root 用户
>
> su

>2.修改 sshd_config
>
> vim /etc/ssh/sshd_config
```shell
...
#PermitRootLogin prohibit-password
PermitRootLogin yes			# 允许root直接登录
...
#PermitEmptyPasswords no
PermitEmptyPasswords no		# 因为设置了root密码，所以需要修改为no
...
```

>3.重启 ssh 服务
> systemctl restart ssh

## Ubuntu20.04 下彻底卸载apache2
```shell
//1. 删除apache
sudo apt-get --purge remove apache2
sudo apt-get --purge remove apache2.2-common
 
//2.找到没有删除掉的配置文件，一并删除
sudo find /etc -name "*apache*" |xargs  rm -rf 
sudo rm -rf /var/www
sudo rm -rf /etc/libapache2-mod-jk
 
//3.删除关联，这样就可以再次用apt-get install apache2 重装了
#dpkg -l |grep apache2|awk '{print $2}'|xargs dpkg -P//注意：这一步可能会报错，但也没关系
 
//ok了，大家可以试试!!!
```
