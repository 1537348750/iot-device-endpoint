package org.lgq.iot.web.config;


import lombok.extern.slf4j.Slf4j;
import org.lgq.iot.sdk.mqtt.utils.ExceptionUtil;
import org.lgq.iot.sdk.mqtt.utils.StringUtil;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
public class ContainerIPConfig {

    private static String containerIp;

    public static String getContainerIp() {
        if (StringUtil.isBlank(containerIp)) {
            init();
        }
        return containerIp;
    }

    public static void init() {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            containerIp = addr.getHostAddress();
            log.info("Current container IP address is: {}", containerIp);
        } catch (UnknownHostException e) {
            log.error("Could not determine IP address of container, e = {}", ExceptionUtil.getBriefStackTrace(e));
        }
    }
}
