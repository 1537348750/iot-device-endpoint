package org.lgq.iot.sdk.mqtt.exception;

public class IoTMqttException extends RuntimeException{

    private String code;

    public IoTMqttException(IotCode iotCode) {
        super(iotCode.getMessage());
        this.code = iotCode.getCode();
    }

    public IoTMqttException(IotCode iotCode, Throwable throwable) {
        super(iotCode.getMessage(), throwable);
        this.code = iotCode.getCode();
    }

    public String getCode() {
        return code;
    }
}
