FROM registry.cn-hangzhou.aliyuncs.com/dragonwell/dragonwell:dragonwell-8.8.9_jdk8u302-ga_x86_64_slim
COPY target/shiro-header-limit-lab-1.0.jar /app.jar
COPY flag.txt /flag.txt
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar","--server.port=8080"]
