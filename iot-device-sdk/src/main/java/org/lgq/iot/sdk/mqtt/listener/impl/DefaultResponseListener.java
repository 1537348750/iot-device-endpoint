package org.lgq.iot.sdk.mqtt.listener.impl;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.lgq.iot.sdk.mqtt.client.MqttClient;
import org.lgq.iot.sdk.mqtt.listener.CustomResponseListener;

public class DefaultResponseListener implements CustomResponseListener {

    @Override
    public void resopnse(MqttClient client, String topic, MqttMessage message) {
        // TODO 完善命令响应
    }
}
