package org.lgq.iot.sdk.mqtt.exception;


import lombok.Getter;

@Getter
public enum IotCode {

    // success
    SUCCESS("200", "Success"),


    // failed
    FAILED("IoT.000500", "Internal service error."),
    DEVICE_ONLINE_ALREADY("IoT.000501", "The device has already oline"),

    ;

    private final String code;
    private final String message;

    IotCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
