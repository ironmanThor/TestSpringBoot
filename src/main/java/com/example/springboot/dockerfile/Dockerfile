FROM openjdk:8u222-jre
VOLUME /tmp
ADD target/pc-admin-service-0.0.1-SNAPSHOT.jar app.jar   # ADD命令把jar复制过去，改名为app.jar
ENV JAVA_OPTS=""
ENV LANG C.UTF-8
ENV TZ=Asia/Shanghai
RUN sh -c 'touch /app.jar'      # touch命令的作用是修改这个文件的访问时间和修改时间为当前时间，而不会修改文件的内容。
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /app.jar" ]     # "sh -c" 命令，它可以让 bash 将一个字串作为完整的命令来执行 java启动jar文件