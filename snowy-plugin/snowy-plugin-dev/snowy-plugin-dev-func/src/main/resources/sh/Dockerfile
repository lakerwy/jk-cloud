# 使用Alpine Linux作为基础镜像，减小镜像体积
FROM alpine:latest

# 维护人信息
MAINTAINER myk <jk@gmail.com>

# 更换为阿里云的镜像源
RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g' /etc/apk/repositories

# 安装必要的工具和依赖
RUN apk update && \
    (apk add --no-cache wget openjdk8 nginx supervisor redis) && \
    rm -rf /var/cache/apk/*


# 设置工作目录
WORKDIR /app

# 将jar包添加到容器中并更名为app.jar
ADD ./server/snowy-web-app/target/snowy-web-app-2.0.0.jar app.jar

# 复制前端项目到nginx默认的资源目录里
COPY ./web/dist /usr/share/nginx/html

# 复制云盘项目到nginx默认的资源目录里
COPY ./yunpan/dist /usr/share/nginx/html/yunpan

# 复制nginx配置文件到docker里nginx默认的配置文件位置
COPY ./conf/nginx.conf /etc/nginx/nginx.conf

# 复制Redis配置文件到容器内
COPY ./conf/redis.conf /etc/redis.conf

# 复制外部配置文件到容器内
COPY ./conf/application.yaml /app/application.yaml

# 暴露80端口
EXPOSE 80

# 启动Supervisor，它会同时启动Redis、Nginx和Spring Boot应用
CMD redis-server /etc/redis.conf && nginx -g "daemon off;" & java -Djava.security.egd=file:/dev/./urandom -jar app.jar --spring.config.location=file:/app/application.yaml && supervisord -c /etc/supervisord.conf
