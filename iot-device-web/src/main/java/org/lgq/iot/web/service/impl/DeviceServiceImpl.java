package org.lgq.iot.web.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.lgq.iot.sdk.mqtt.client.MqttClient;
import org.lgq.iot.sdk.mqtt.utils.ExceptionUtil;
import org.lgq.iot.web.cache.ClientCache;
import org.lgq.iot.web.dto.DeviceInfoDto;
import org.lgq.iot.web.dto.MessageUpDto;
import org.lgq.iot.web.dto.SubscribeTopics;
import org.lgq.iot.web.service.DeviceService;
import org.springframework.stereotype.Service;

@Slf4j
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
        client.connect(deviceInfoDto.getIsSSL());
        ClientCache.cacheClient(deviceInfoDto.getDeviceId(), client);
        // TODO 设备上线后打印设备客户端缓存所在的节点
    }

    @Override
    public void offline(String deviceId) {
        ClientCache.removeClient(deviceId);
    }

    @Override
    public void messageUp(MessageUpDto messageUpDto) {
        MqttClient client = ClientCache.getClient(messageUpDto.getDeviceId());
        try {
            client.messagesUp(messageUpDto.getContent(), messageUpDto.getQos(), null);
        } catch (MqttException e) {
            log.error("messages up fail, e = {}", ExceptionUtil.getBriefStackTrace(e));
        } catch (Exception e) {
            log.error("messages up fail, ex = {}", ExceptionUtil.getBriefStackTrace(e));
        }
    }

    @Override
    public void subscribeTopics(SubscribeTopics subScribeTopics) {
        MqttClient client = ClientCache.getClient(subScribeTopics.getDeviceId());
        client.subScribeTopics(subScribeTopics.getTopics(), null);
        ClientCache.cacheClient(subScribeTopics.getDeviceId(), client);
    }
}
