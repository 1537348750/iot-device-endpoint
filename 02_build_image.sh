#!/bin/bash

# 获取当前的东八区时间(北京时间)
date=$(TZ=Asia/Shanghai date +%Y.%m%d.%H.%M.%S)
# 镜像名称
image_name="iot-device-endpoint-$date"

# 解压
unzip iot-device-endpoint-package.zip
# 以当前目录下的Dockerfile构建镜像
docker build --no-cache -t "$image_name" .
# 构建镜像并打印日志
# docker build --no-cache -t "$image_name" . | tee build_docker_image.log

# 删除资源
rm -f iot-device-endpoint-package.zip
rm -rf lib/
rm -f Dockerfile
rm -f 02_build_image.sh
rm -f 03_start.sh

echo "Docker image:$image_name build success !"
