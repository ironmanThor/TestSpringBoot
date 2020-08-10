#!/bin/bash
mvn package
echo '项目打包完成'
docker login --username=还好遗憾念 registry.cn-hangzhou.aliyuncs.com
echo '登录密码'
docker build -t registry.cn-hangzhou.aliyuncs.com/test_1192919554/test_my:latest .
echo '镜像创建完成'
docker push registry.cn-hangzhou.aliyuncs.com/test_1192919554/test_my:latest
echo '镜像推送完成'
