#!/bin/bash

unzip -o iot-device-web-package.zip

date=$(TZ=Asia/Shanghai date +%Y.%m%d.%H%M.%S)
image_name="iot-device-web-$date"

docker build --no-cache -t "$image_name" .

rm -rf lib/
rm -f start.sh
rm -f Dockerfile
rm -f iot-device-web-package.zip
echo "docker image built: $image_name"
