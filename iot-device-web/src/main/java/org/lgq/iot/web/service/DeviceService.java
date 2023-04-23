package org.lgq.iot.web.service;

import org.lgq.iot.web.dto.DeviceInfoDto;
import org.lgq.iot.web.dto.SubscribeTopics;

public interface DeviceService {

    void online(DeviceInfoDto deviceInfoDto);

    void offline(String deviceId);

    void subscribeTopics(SubscribeTopics subScribeTopics);

    void messageUp(String json, String deviceId);

    void eventUp(String json, String deviceId);

    void propertiesReport(String json, String deviceId);

    void gatewaySubDevicePropertiesReport(String json, String deviceId);


}
