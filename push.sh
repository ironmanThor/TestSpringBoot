#!/bin/bash
mvn package
echo '项目打包完成'
docker login --username=还好遗憾念 registry.cn-hangzhou.aliyuncs.com
echo '登录密码'
if [ -z $1 ]; then
  docker build -t registry.cn-hangzhou.aliyuncs.com/test_1192919554/test_my:latest .
  echo '镜像创建完成1'
  docker push registry.cn-hangzhou.aliyuncs.com/test_1192919554/test_my:latest
  echo '镜像推送完成1'
else
  docker build -t registry.cn-hangzhou.aliyuncs.com/test_1192919554/test_my:$1 .
  echo '镜像创建完成2'
  docker push registry.cn-hangzhou.aliyuncs.com/test_1192919554/test_my:$1
  echo '镜像推送完成2'
fi
echo '执行成功！'