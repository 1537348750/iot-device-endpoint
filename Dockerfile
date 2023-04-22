FROM openjdk:8-jdk-alpine

COPY target/myapp.tar.gz /usr/local/
RUN tar -xzf /usr/local/myapp.tar.gz -C /usr/local/

WORKDIR /usr/local/myapp
ENV PATH="/usr/local/myapp:${PATH}"

CMD ["java", "-jar", "app.jar"]