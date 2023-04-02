package org.lgq.iot.web.dto;


import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceInfoDto {

    private String serviceIp;

    private String deviceId;

    private String secret;

    private Integer qos;

    private Boolean isSSL;
}
