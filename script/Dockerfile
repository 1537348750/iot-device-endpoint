
FROM openjdk:8-jre-alpine
# FROM openjdk:8-jre-slim
# FROM openjdk:8-jre
# FROM adoptopenjdk:8-jre-hotspot
# FROM amazoncorretto:8

LABEL maintainer="LGQ"

ENV APP_HOME /iot/iot-device-web
ENV APP_NAME iot-device-web-1.0.0.jar

RUN mkdir -p /iot \
        $APP_HOME/lib \
        $APP_HOME/script \
        $APP_HOME/logs/history

RUN addgroup -S paas && adduser -S -G paas paas
RUN chown -R paas:paas /iot && chmod -R 755 /iot

# copy file
COPY lib $APP_HOME/lib
COPY start.sh $APP_HOME/script
COPY Dockerfile $APP_HOME/script

WORKDIR $APP_HOME
EXPOSE 8001
USER paas
CMD ["sh", "/iot/iot-device-web/script/start.sh"]



# 构建Docker镜像时，选择合适的基础镜像非常重要，它会影响到你的应用程序的运行效率、安全性等方面。
# 除了openjdk:8-jre-alpine之外，还有其他一些常用的基础镜像可供选择，下面是一些常见的基础镜像：

##  openjdk:8-jre-slim  --: 基于Debian Linux构建的OpenJDK 8 JRE镜像，并且该镜像相对较小，适合于构建轻量级应用程序镜像。
##  openjdk:8-jre  --: 基于Debian Linux构建的OpenJDK 8 JRE镜像，与openjdk:8-jre-alpine相比，这个镜像可能稍微大一些，但是性能更好。
##  adoptopenjdk:8-jre-hotspot  --: AdoptOpenJDK项目提供的OpenJDK 8 HotSpot JRE镜像，这个镜像提供了更好的性能和更广泛的平台支持。
##  amazoncorretto:8  --: Amazon提供的OpenJDK 8镜像，这个镜像基于Amazon Linux 2构建，提供了更好的性能和安全性。
# 综上所述，选择合适的基础镜像取决于你的应用程序的需求和目标。
# 如果你的应用程序需要更小的镜像，可以选择openjdk:8-jre-slim或openjdk:8-jre-alpine；
# 如果你需要更好的性能，可以选择openjdk:8-jre或adoptopenjdk:8-jre-hotspot；
# 如果你需要更好的安全性，可以选择amazoncorretto:8。

