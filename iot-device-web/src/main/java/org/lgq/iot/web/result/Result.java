package org.lgq.iot.web.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 全局统一返回结果类
 *
 */
@Data
@ApiModel(value = "全局统一返回结果")
public class Result {

    @ApiModelProperty(value = "响应状态码")
    private String code;

    @ApiModelProperty(value = "响应信息")
    private String message;

    @ApiModelProperty(value = "响应数据")
    private Object data;

    private Result(){}

    public static Result success(IotCode iotCode) {
        Result result = new Result();
        result.setCode(iotCode.getCode());
        result.setMessage(iotCode.getMessage());
        return result;
    }

    public static Result failed(IotCode iotCode) {
        Result result = new Result();
        result.setCode(iotCode.getCode());
        result.setMessage(iotCode.getMessage());
        return result;
    }

    public Result message(String message) {
        this.message = message;
        return this;
    }

    public Result data(Object data) {
        this.data = data;
        return this;
    }

    private void setCode(String code) {
        this.code = code;
    }

    private void setMessage(String message) {
        this.message = message;
    }

    private void setData(Object data) {
        this.data = data;
    }
}
