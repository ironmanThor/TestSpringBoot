#!/bin/bash
mvn clean package -Dmaven.test.skip=true
docker build -t registry.cn-beijing.aliyuncs.com/jinan-teaching/pc-admin-service:latest .
docker push registry.cn-beijing.aliyuncs.com/jinan-teaching/pc-admin-service:latest
echo '执行成功!'