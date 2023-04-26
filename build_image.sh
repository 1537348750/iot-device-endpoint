#!/bin/bash

# decompress iot-device-web-package.zip
unzip iot-device-web-package.zip

# Get the current Dongba District time(BeiJin Time)
date=$(TZ=Asia/Shanghai date +%Y.%m%d.%H.%M.%S)
# image name
image_name="iot-device-web-$date"

# Build the image with the Dockerfile in the current directory
docker build --no-cache -t "$image_name" .

# delete resources
rm -f iot-device-web-package.zip
rm -rf lib/
rm -f Dockerfile
rm -f start.sh

echo "Docker image:$image_name build success !"

# 利用dockerfile构建镜像有没有比openjdk:8-jre-alpine更合适的基础镜像？
# 构建Docker镜像时，选择合适的基础镜像非常重要，它会影响到你的应用程序的运行效率、安全性等方面。
# 除了openjdk:8-jre-alpine之外，还有其他一些常用的基础镜像可供选择，下面是一些常见的基础镜像：

#  openjdk:8-jre-slim  --: 基于Debian Linux构建的OpenJDK 8 JRE镜像，并且该镜像相对较小，适合于构建轻量级应用程序镜像。
#  openjdk:8-jre  --: 基于Debian Linux构建的OpenJDK 8 JRE镜像，与openjdk:8-jre-alpine相比，这个镜像可能稍微大一些，但是性能更好。
#  adoptopenjdk:8-jre-hotspot  --: AdoptOpenJDK项目提供的OpenJDK 8 HotSpot JRE镜像，这个镜像提供了更好的性能和更广泛的平台支持。
#  amazoncorretto:8  --: Amazon提供的OpenJDK 8镜像，这个镜像基于Amazon Linux 2构建，提供了更好的性能和安全性。
# 综上所述，选择合适的基础镜像取决于你的应用程序的需求和目标。
# 如果你的应用程序需要更小的镜像，可以选择openjdk:8-jre-slim或openjdk:8-jre-alpine；
# 如果你需要更好的性能，可以选择openjdk:8-jre或adoptopenjdk:8-jre-hotspot；
# 如果你需要更好的安全性，可以选择amazoncorretto:8。