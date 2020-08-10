# 基于哪个镜像

FROM openjdk:8u222-jre

# 将本地文件夹挂载到当前容器

VOLUME /tmp

# 拷贝文件到容器，handcuffs-reg-0.0.1-SNAPSHOT.jar这里是maven打包后的名字

ADD target/*.jar app.jar

ENV JAVA_OPTS=""
ENV LANG C.UTF-8
ENV TZ=Asia/Shanghai
RUN sh -c 'touch /app.jar'

# 配置容器启动后执行的命令

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /app.jar" ]
