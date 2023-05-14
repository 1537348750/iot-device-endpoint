package org.lgq.iot.web;

import lombok.extern.slf4j.Slf4j;
import org.lgq.iot.web.config.IPConfig;
import org.lgq.iot.sdk.mqtt.utils.SysConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication(
        scanBasePackages = {"org.lgq.iot.web"}
)
@EnableScheduling
@Slf4j
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        log.info("< >");
        log.info("<============= start iot-device-web success =============>");
        log.info("< >");

        IPConfig.init();
        SysConfig.init();
    }
}