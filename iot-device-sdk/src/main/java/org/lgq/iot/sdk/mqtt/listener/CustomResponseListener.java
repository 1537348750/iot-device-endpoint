package org.lgq.iot.sdk.mqtt.listener;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.lgq.iot.sdk.mqtt.client.MqttClient;

/**
 * 命令下发监听器
 */
public interface CustomResponseListener {

    void resopnse(MqttClient client, String topic, MqttMessage message);
}
