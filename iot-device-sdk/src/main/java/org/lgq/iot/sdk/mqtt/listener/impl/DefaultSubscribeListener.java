package org.lgq.iot.sdk.mqtt.listener.impl;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttToken;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttSuback;
import org.lgq.iot.sdk.mqtt.listener.CustomSubscribeListener;

@Slf4j
public class DefaultSubscribeListener implements CustomSubscribeListener {

    @Override
    public void onSuccess(IMqttToken iMqttToken) {
        MqttToken mqttToken = (MqttToken) iMqttToken;
        MqttSuback mqttSuback = (MqttSuback) mqttToken.getResponse();
        String[] topics = mqttToken.getTopics();
        int[] grantedQos = mqttSuback.getGrantedQos();
        if (grantedQos[0] > 2 || grantedQos[0] < 0) {
            log.error("Subscribe mqtt topic fail, topic={}, qos={}", topics[0], grantedQos[0]);
        }
        log.info("Subscribe mqtt topic success, topic={}, qos={}", topics[0], grantedQos[0]);
    }

    @Override
    public void onFailure(IMqttToken iMqttToken, Throwable e) {
        log.error("Subscribe mqtt topic fail, topic={}", iMqttToken.getTopics()[0], e);
    }
}
