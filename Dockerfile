# base image
FROM openjdk:8-jre-alpine

# image author
LABEL maintainer="LGQ"

# set env variables
ENV IOT_HOME /iot/iot-device-web
ENV IOT_NAME iot-device-web-1.0.0.jar

# create image path:/iot/iot-device-web/lib and copy 'lib' to this path
RUN mkdir -p $IOT_HOME/lib
COPY lib $IOT_HOME/lib

# create image path:/iot/iot-device-web/script and copy script file to this path
RUN mkdir -p $IOT_HOME/script
COPY start.sh $IOT_HOME/script
COPY Dockerfile $IOT_HOME/script
COPY build_image.sh $IOT_HOME/script

# work path
WORKDIR $IOT_HOME

# exposed port
EXPOSE 8001

# start(with start.sh)
CMD ["java", "-jar", "/iot/iot-device-web/lib/iot-device-web-1.0.0.jar"]
#USER paas