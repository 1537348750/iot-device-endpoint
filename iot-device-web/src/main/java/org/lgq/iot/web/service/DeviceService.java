package org.lgq.iot.web.service;

import org.lgq.iot.web.dto.DeviceInfoDto;
import org.lgq.iot.web.dto.MessageUpDto;
import org.lgq.iot.web.dto.SubscribeTopics;

public interface DeviceService {

    void online(DeviceInfoDto deviceInfoDto);

    void offline(String deviceId);

    void messageUp(MessageUpDto messageUpDto);

    void subscribeTopics(SubscribeTopics subScribeTopics);
}
