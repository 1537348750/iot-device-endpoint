package org.lgq.iot.sdk.mqtt.dto;


import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 命令响应DTO
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommandsRespDTO {

    @JsonAlias("result_code")
    private final Integer result_code = 0;

    @JsonAlias("response_name")
    private final String response_name = "COMMAND_RESPONSE";

    @JsonAlias("paras")
    private Map<String, Object> paras;
}
