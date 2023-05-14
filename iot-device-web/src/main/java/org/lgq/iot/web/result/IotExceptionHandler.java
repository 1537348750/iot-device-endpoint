package org.lgq.iot.web.result;

import lombok.extern.slf4j.Slf4j;
import org.lgq.iot.sdk.mqtt.exception.IoTMqttException;
import org.lgq.iot.sdk.mqtt.exception.IotCode;
import org.lgq.iot.sdk.mqtt.utils.ExceptionUtil;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "org.lgq.iot")
public class IotExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result error(Exception e) {
        log.error(ExceptionUtil.getBriefStackTrace(e));
        return Result.failed(IotCode.FAILED).message(e.getMessage());
    }

    /**
     * 自定义异常处理方法
     * @param e
     * @return
     */
    @ExceptionHandler(IoTMqttException.class)
    public Result error(IoTMqttException e) {
        log.error(ExceptionUtil.getBriefStackTrace(e));
        return Result.failed(e.getCode()).message(e.getMessage());
    }


}
