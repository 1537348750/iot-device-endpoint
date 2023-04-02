package org.lgq.iot.sdk.mqtt;


import org.lgq.iot.sdk.mqtt.client.MqttClient;

public class TestMqttClient {

    public static void mains(String[] args) throws InterruptedException {
        String serviceIp = "5524445c4a.st1.iotda-device.cn-north-4.myhuaweicloud.com";
        String deviceId = "lgq_mqtt_00_88888888";
        String secret = "88888888";
        MqttClient client = new MqttClient(serviceIp, deviceId, secret);

        //client.connect(true);
        Thread.sleep(3000);

    }
}