package org.lgq.iot.web;

import lombok.extern.slf4j.Slf4j;
import org.lgq.iot.sdk.mqtt.utils.ExceptionUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.net.InetAddress;
import java.net.UnknownHostException;


@SpringBootApplication(
        exclude = DataSourceAutoConfiguration.class,
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

        try {
            InetAddress addr = InetAddress.getLocalHost();
            String ip = addr.getHostAddress();
            log.info("Current container IP address is: {}", ip);
        } catch (UnknownHostException e) {
            log.error("Could not determine IP address of container, e = {}", ExceptionUtil.getBriefStackTrace(e));
        }
    }
}