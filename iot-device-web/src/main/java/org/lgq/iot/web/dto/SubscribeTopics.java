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

    private List<String> topics;

}
