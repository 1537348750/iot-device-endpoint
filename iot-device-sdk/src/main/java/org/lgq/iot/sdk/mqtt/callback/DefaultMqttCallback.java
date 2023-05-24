package org.lgq.iot.sdk.mqtt.callback;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.lgq.iot.sdk.mqtt.client.MqttClient;
import org.lgq.iot.sdk.mqtt.listener.CustomResponseListener;
import org.lgq.iot.sdk.mqtt.listener.impl.DefaultResponseListener;
import org.lgq.iot.sdk.mqtt.utils.ExceptionUtil;

/**
 * Mqtt回调
 */
@Slf4j
public class DefaultMqttCallback implements CustomMqttCallback {

    private MqttClient client;
    private CustomResponseListener responseListener;

    public DefaultMqttCallback(MqttClient client, CustomResponseListener responseListener) {
        this.client = client;
        this.responseListener = responseListener == null ? new DefaultResponseListener() : responseListener;
    }

    public DefaultMqttCallback(MqttClient client) {
        this(client, new DefaultResponseListener());
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        log.info("Receive mqtt topic={}, message={}\n", topic, message);
        responseListener.response(client, topic, message);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        //log.info("Mqtt message deliver complete");
    }

    @Override
    public void connectComplete(boolean reconnect, String serviceURI) {
        log.info("Mqtt client connected, address={}, reconnect={}", serviceURI, reconnect);
        client.subScribeDefaultTopics();
    }

    @Override
    public void connectionLost(Throwable throwable) {
        log.error("Connection lost.... e = {}", ExceptionUtil.getBriefStackTrace(throwable));
        // TODO 可在此处实现重连
    }
}
