#!/usr/bin/env bash
mvn clean package -Dmaven.test.skip=true
echo '项目打包完成'
docker build -t docker.dapeng.lan/teaching/pc-admin-service:latest .
echo '镜像创建完成'
docker push docker.dapeng.lan/teaching/pc-admin-service:latest
echo '镜像推送完成'