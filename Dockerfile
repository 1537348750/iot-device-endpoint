# 基础镜像
FROM openjdk:8-jre-alpine

# 镜像作者信息
LABEL maintainer="LGQ"

# 设置环境变量
ENV APP_HOME /iot/iot-device-endpoint
ENV APP_NAME iot-device-web-1.0.0.jar

# 创建应用目录并将当前目录下的 lib 目录复制到应用目录下
RUN mkdir -p $APP_HOME/lib
COPY lib $APP_HOME/lib

# 复制当前目录下的 iot-device-web-1.0.0.jar 文件到应用目录下
# COPY iot-device-web-1.0.0.jar $APP_HOME

# 设置工作目录
WORKDIR $APP_HOME

# 暴露应用端口
EXPOSE 8001

# 启动应用(待更换命令执行start.sh启动)
CMD ["java", "-jar", "/iot/iot-device-endpoint/lib/iot-device-web-1.0.0.jar"]
#USER paas