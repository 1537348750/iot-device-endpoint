package org.lgq.iot.web.result;


import lombok.Getter;

@Getter
public enum IotCode {
    // success
    SUCCESS("IoT.000200", "Success"),


    // failed
    FAILED("IoT.000500", "Internal service error."),

    ;

    private final String code;

    private final String message;

    IotCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
