package org.lgq.iot.web.config;


import lombok.extern.slf4j.Slf4j;
import org.lgq.iot.sdk.mqtt.utils.ExceptionUtil;
import org.lgq.iot.sdk.mqtt.utils.StringUtil;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
public class IPConfig {

    private static String containerIp;

    public static String getContainerIp() {
        if (StringUtil.isBlank(containerIp)) {
            init();
        }
        return containerIp;
    }

    public static void init() {
        try {
            containerIp = InetAddress.getLocalHost().getHostAddress();
            log.info("Current container IP address is: {}", containerIp);
        } catch (UnknownHostException e) {
            log.error("Could not get IP address of container, e = {}", ExceptionUtil.getBriefStackTrace(e));
        }
    }
}
