package org.lgq.iot.web.service.impl;

import org.lgq.iot.sdk.mqtt.client.MqttClient;
import org.lgq.iot.web.cache.ClientCache;
import org.lgq.iot.web.dto.DeviceInfoDto;
import org.lgq.iot.web.dto.MessageUpDto;
import org.lgq.iot.web.dto.SubscribeTopics;
import org.lgq.iot.web.service.DeviceService;
import org.springframework.stereotype.Service;

@Service
public class DeviceServiceImpl implements DeviceService {

    @Override
    public void online(DeviceInfoDto deviceInfoDto) {
        // check params
        MqttClient client = new MqttClient(
                deviceInfoDto.getServiceIp(),
                deviceInfoDto.getDeviceId(),
                deviceInfoDto.getSecret(),
                deviceInfoDto.getQos());
        ClientCache.cacheDeviceClient(deviceInfoDto.getDeviceId(), client);
        client.connect(deviceInfoDto.getIsSSL());
    }

    @Override
    public void offline(String deviceId) {
        ClientCache.removeDeviceClient(deviceId);
    }

    @Override
    public void messageUp(MessageUpDto messageUpDto) {
        MqttClient client = ClientCache.getDeviceClient(messageUpDto.getDeviceId());
        client.messagesUp(messageUpDto.getContent(), messageUpDto.getQos(), null);
    }

    @Override
    public void subscribeTopics(SubscribeTopics subScribeTopics) {
        MqttClient client = ClientCache.getDeviceClient(subScribeTopics.getDeviceId());
        client.subScribeTopics(subScribeTopics.getTopics());
    }
}
