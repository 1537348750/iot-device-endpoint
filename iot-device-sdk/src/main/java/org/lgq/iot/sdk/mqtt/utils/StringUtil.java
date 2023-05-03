package org.lgq.iot.sdk.mqtt.utils;

public class StringUtil {

    public static boolean isBlank(String string) {
        if (string == null) {
            return true;
        } else {
            return string.trim().length() == 0;
        }
    }
}
