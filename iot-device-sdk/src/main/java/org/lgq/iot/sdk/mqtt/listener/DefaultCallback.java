package org.lgq.iot.sdk.mqtt.listener;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.lgq.iot.sdk.mqtt.client.MqttClient;

/**
 * Mqtt回调
 */
@Slf4j
public class DefaultCallback implements MqttCallbackExtended {

    private MqttClient client;

    public DefaultCallback(MqttClient client) {
        this.client = client;
    }

    @Override
    public void connectComplete(boolean reconnect, String serviceURI) {
        log.info("Mqtt client connected, address={}, reconnect={}", serviceURI, reconnect);
        client.subScribeDefaultTopics();
    }

    @Override
    public void connectionLost(Throwable throwable) {
        log.warn("Connection lost....");
        throwable.printStackTrace();
        // 可在此处实现重连
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        log.info("Receive mqtt topic={}, message={}", topic, message);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        log.info("Mqtt message deliver complete.");
    }
}
