package org.lgq.iot.sdk.mqtt.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceInfo {
    private String clientId;
    private String password;
}
