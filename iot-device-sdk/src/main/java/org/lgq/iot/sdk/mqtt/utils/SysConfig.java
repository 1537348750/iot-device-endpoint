package org.lgq.iot.sdk.mqtt.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SysConfig {

    public static final String WINDOWS = "WINDOWS";
    public static final String LINUX = "LINUX";

    private static String SYSTEM_TYPE;

    public static void init() {
        String osName = System.getProperty("os.name");
        if (osName.startsWith("Windows")) {
            SYSTEM_TYPE = WINDOWS;
        } else if (osName.startsWith("Linux")) {
            SYSTEM_TYPE = LINUX;
        } else {
            SYSTEM_TYPE = null;
        }
        log.info("System type is [{}]", SYSTEM_TYPE);
    }

    public static String getSystemType() {
        if (StringUtil.isBlank(SYSTEM_TYPE)) {
            init();
        }
        return SYSTEM_TYPE;
    }
}
