
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
CMD ["java", "-jar", "/iot/iot-device-web/lib/iot-device-web-1.0.0.jar"]