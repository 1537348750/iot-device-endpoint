package org.lgq.iot.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication(
        exclude = DataSourceAutoConfiguration.class,
        scanBasePackages = {"org.lgq.iot.web"}
)
@Slf4j
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        log.info("< >");
        log.info("<============= start iot-device-web success =============>");
        log.info("< >");
    }
}