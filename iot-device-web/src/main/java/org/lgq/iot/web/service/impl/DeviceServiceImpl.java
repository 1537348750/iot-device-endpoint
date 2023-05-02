package org.lgq.iot.web.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.lgq.iot.sdk.mqtt.client.MqttClient;
import org.lgq.iot.web.cache.ClientCache;
import org.lgq.iot.web.dto.DeviceInfoDto;
import org.lgq.iot.web.dto.SubscribeTopics;
import org.lgq.iot.web.service.DeviceService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DeviceServiceImpl implements DeviceService {

    @Override
    public void online(DeviceInfoDto deviceInfoDto) {
        ClientCache.removeClient(deviceInfoDto.getDeviceId());
        MqttClient client  = new MqttClient(
                deviceInfoDto.getServiceIp(),
                deviceInfoDto.getDeviceId(),
                deviceInfoDto.getSecret(),
                deviceInfoDto.getQos());
        client.connect(deviceInfoDto.getIsSSL());
        ClientCache.cacheClient(deviceInfoDto.getDeviceId(), client);
        // TODO 设备上线后打印设备客户端缓存所在的节点
    }

    @Override
    public void offline(String deviceId) {
        ClientCache.removeClient(deviceId);
    }

    @Override
    public void subscribeTopics(SubscribeTopics subScribeTopics) {
        MqttClient client = ClientCache.getClient(subScribeTopics.getDeviceId());
        client.subScribeTopics(subScribeTopics.getTopics(), null);
        ClientCache.cacheClient(subScribeTopics.getDeviceId(), client);
    }

    @Override
    public void messageUp(String json, String deviceId) {
        MqttClient client = ClientCache.getClient(deviceId);
        try {
            client.messagesUp(json, null, null);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void eventUp(String json, String deviceId) {
        MqttClient client = ClientCache.getClient(deviceId);
        try {
            client.eventUp(json, null, null);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void propertiesReport(String json, String deviceId) {
        MqttClient client = ClientCache.getClient(deviceId);
        try {
            client.propertiesReport(json, null, null);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void gatewaySubDevicePropertiesReport(String json, String deviceId) {
        MqttClient client = ClientCache.getClient(deviceId);
        try {
            client.gatewaySubDevicePropertiesReport(json, null, null);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }
}
