# 一、靶场介绍
本靶场基于shiro漏洞构建，对其利用过程中进行限制，需要绕过限制获取权限后拿到flag。

# 二、环境搭建
### 启动靶场

进入项目目录：

```
cd shiro-header-limit-lab
```

构建镜像：

```
docker build -t shiro-lab .
```

启动容器：

```
docker run -d –-name shiro-lab -p 0.0.0.0:8080:8080 shiro-lab
```

访问靶场

```bash
访问：http://127.0.0.1:8080
```

---

# 三、wp
请关注凌曦安全公众号，并回复shirolab-wp

<img src="https://github.com/Syst1msec/Syst1msec/raw/main/TeamLogo.jpg" width="350">

