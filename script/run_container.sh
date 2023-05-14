#!/bin/bash

image_name=$1

if [ -z "$image_name" ]; then
  echo "ERROR: image_name is null"
  exit 1
fi

# 在宿主机的日志目录添加所有用户的"读/写/执行"权限，保证容器的日志文件能够正常挂载到宿主机
mkdir -p /iot/container/iot-device-web/logs/history
chmod -R 777 /iot/container/iot-device-web/logs

# 挂载日志、历史日志文件
docker run -e TZ=Asia/Shanghai -p 8001:8001 --cpus=1 --memory=4g \
  -v /iot/container/iot-device-web/logs:/iot/iot-device-web/logs \
  -v /iot/container/iot-device-web/logs/history:/iot/iot-device-web/logs/history \
  --name "$image_name" -d  "$image_name"

# 删除自身
# rm -f "$0"
