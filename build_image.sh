#!/bin/bash

# decompress iot-device-web-package.zip
unzip iot-device-web-package.zip

# Get the current Dongba District time(BeiJin Time)
date=$(TZ=Asia/Shanghai date +%Y.%m%d.%H.%M.%S)
# image name
image_name="iot-device-web-$date"

# Build the image with the Dockerfile in the current directory
docker build --no-cache -t "$image_name" .
# Build the image and print the log
# docker build --no-cache -t "$image_name" . | tee build_docker_image.log

# delete resources
rm -f iot-device-web-package.zip
rm -rf lib/
rm -f Dockerfile
rm -f start.sh

echo "Docker image:$image_name build success !"
