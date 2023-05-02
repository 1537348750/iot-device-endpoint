package org.lgq.iot.web.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscribeTopics {

    private String deviceId;

    private Integer qos;

    private List<String> topics;

}
