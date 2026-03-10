# 一、靶场介绍
本靶场基于 **Java CommonsCollections6（CC6）反序列化漏洞** 构建，同时结合 **前端 JavaScript 逆向分析** 场景，模拟真实 Web 应用中常见的安全问题，在靶场中，首先需要对目标站点的 **前端 JavaScript 代码进行逆向分析**，绕过其限制，获取能够正常与后端交互的请求参数。随后，通过分析后端的 **Java 反序列化处理逻辑**，构造恶意序列化数据，并结合 **CommonsCollections6 Gadget Chain** 生成利用 payload，在服务器反序列化过程中触发命令执行。

# 二、环境搭建

### 环境要求

- Docker ≥ 20.x  
- Docker Compose ≥ 2.x  

确认 Docker 环境是否安装：

```bash
docker -v
docker compose version
```

### 启动靶场

进入项目目录：

```bash
cd cc6-range
```

构建镜像：

```bash
docker build -t cc6-range .
```

启动容器：

```bash
docker compose up --build -d
```

### 访问靶场

浏览器访问：

```
http://127.0.0.1:8080
```

如需停止靶场：

```bash
docker compose down
```

---

# 三、WP
请关注凌曦安全公众号，并回复 cc6-wp

<img src="https://github.com/Syst1msec/Syst1msec/raw/main/TeamLogo.jpg" width="350">

