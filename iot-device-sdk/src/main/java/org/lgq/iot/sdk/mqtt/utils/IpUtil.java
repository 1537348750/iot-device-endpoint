package org.lgq.iot.sdk.mqtt.utils;

import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
public class IpUtil {

    public static String parseIP(String host) {
        try {
            return InetAddress.getByName(host).getHostAddress();
        } catch (UnknownHostException e) {
            log.error("parse ip:[{}] fail, e = {}", host, ExceptionUtil.getBriefStackTrace(e));
            return host;
        }
    }
}
