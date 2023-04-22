package org.lgq.iot.sdk.mqtt.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;


/**
 * 命令下发DTO
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommandsDownDTO {

    @JsonAlias("object_device_id")
    private String object_device_id;

    @JsonAlias("command_name")
    private String command_name;

    @JsonAlias("service_id")
    private String service_id;

    @JsonAlias("paras")
    private Map<String, Object> paras;
}
