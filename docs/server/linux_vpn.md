# linux下配置clash

[参考链接](https://www.shuzhiduo.com/A/kvJ3Nwq9zg/)

## 安装部署
clash 是一款用 Go 语言开发的软件，所以我可以直接下载预编译的版本进行部署。

下载地址：https://github.com/Dreamacro/clash/releases/download/v1.11.4/clash-linux-amd64-v1.11.4.gz

软件的作者提供了多种架构下预编译的二进制文件，大家一定要注意区分。我当前下载的版本，适合在 x86_64 架构的 Linux 操作系统中运行。

下载完成后，需要解压，并赋予执行权限。

```shell
root@TencentCloud:/var/clash# wget https://github.com/Dreamacro/clash/releases/download/v1.11.4/clash-linux-amd64-v1.11.4.gz

root@TencentCloud:/var/clash# gzip -d clash-linux-amd64-v1.11.4.gz

root@TencentCloud:/var/clash# chmod +x clash-linux-amd64-v1.11.4

root@TencentCloud:/var/clash# mv clash-linux-amd64-v1.11.4 /usr/local/bin/clash

```
Go 语言开发的程序，其打包出的二进制文件不需要其他依赖，可以直接运行。

```shell
clash -v
```

得到如下返回时，意味着 clash 已经可用了。
```shell
Clash v1.8.0 linux amd64 with go1.17.3 Mon Nov  8 13:26:00 UTC 2021
```

## 配置文件
clash 运行需要依赖于一份 YAML 配置文件，默认读取 $HOME/.config/clash/config.yaml 。当没有这份文件的时候，clash 会使用默认配置生成一份，所以我们可以直接运行一下 clash ，来获取模版。

```shell
root@ubuntuserver:~# clash
INFO[0000] Can't find config, create a initial config file
INFO[0000] Can't find MMDB, start download
INFO[0000] Mixed(http+socks) proxy listening at: 127.0.0.1:7890
```

Control + C 退出后，就可以获取默认配置文件了。
```shell
root@ubuntuserver:~# ls $HOME/.config/clash/config.yaml
/root/.config/clash/config.yaml
```

接下来修改这份配置文件，追加我的代理的配置。

原文作者的配置参考：
```shell
mixed-port: 7890
proxies:
  - name: "ss1"
    type: ss
    server: server1_of_ss
    port: 443
    cipher: aes-256-gcm
    password: "password"
  - name: "ss2"
    type: ss
    server: server2_of_ss
    port: 443
    cipher: aes-256-gcm
    password: "password"
  - name: "v2y"
    type: vmess
    server: server3_of_ss
    port: 443
    uuid: uuid
    alterId: 0
    cipher: auto
proxy-groups:
  - name: "auto"
    type: url-test
    proxies:
      - ss1
      - ss2
      - v2y
    url: 'http://www.gstatic.com/generate_204'
    interval: 300
rules:
  - DOMAIN-SUFFIX,google.com,auto
  - DOMAIN-KEYWORD,google,auto
  - DOMAIN,google.com,auto
  - DOMAIN-SUFFIX,github.com,auto
  - DOMAIN-KEYWORD,github,auto
  - DOMAIN,github.com,auto
  - IP-CIDR,127.0.0.0/8,DIRECT
  - GEOIP,CN,DIRECT
  - DST-PORT,80,DIRECT
  - SRC-PORT,7777,DIRECT
  - MATCH,auto
```

>这里我有一份自己的clash配置文件，直接使用就好，缺点就是无法更换节点的选择，可以参考[另一个教程](https://zhuanlan.zhihu.com/p/451863859)进行配置。
但是配置页面暴露在公网比较危险，以后需要的时候再进行配置。

## 运行服务
我希望每次启动 Ubuntu 虚拟机， clash 都可以自动启动，所以我决定将它托管给 systemd来管理。

生成 systemd 配置文件：

```shell
cat > /etc/systemd/system/clash.service << EOF
[Unit]
Description=Clash - A rule-based tunnel in Go
Documentation=https://github.com/Dreamacro/clash/wiki
[Service]
OOMScoreAdjust=-1000
ExecStart=/usr/local/bin/clash -f /root/.config/clash/config.yaml
Restart=on-failure
RestartSec=5
[Install]
WantedBy=multi-user.target
EOF
```

配置开机自启，并启动 clash 服务：

```shell
systemctl enable clash
systemctl start clash
```

## 为 Linux 配置代理

clash 运行起来之后，会在 http://127.0.0.1:7890 这个地址监听 HTTP_PROXY 服务，接下来需要为 Linux 配置代理，使所有的 http 请求，都经由这个代理服务进出。设置很简单，Linux 有专门的环境变量 http_proxy https_proxy 进行相关配置，为了使每次开启终端都可以使环境变量生效，特意做了如下配置：

```shell
echo -e "export http_proxy=http://127.0.0.1:7890\nexport https_proxy=http://127.0.0.1:7890" >> ~/.bashrc
```

>注意：http与https的端口不一定一样，比如我的就是http为7890，https为7891

重新打开一个终端，即可使配置生效。

至此，clash 在 Linux 上的配置都已经完成了。

## 验证效果

使用ping或者使用w3m，作者使用的是w3m

我所使用的 Ubuntu Server 18.04 是不带图形化界面的操作系统。所以我用了一个命令行浏览器 w3m 来验证我的 clash 是否工作正常。

```shell
w3m www.google.com
```

得到图形google.com的返回，说明我的目标已经达成。

