package org.lgq.iot.web.dto;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageUpDto {

    private String deviceId;

    private String topic;

    private Integer qos;

    private String content;

    private Map<String, Object> contentObj;

}
