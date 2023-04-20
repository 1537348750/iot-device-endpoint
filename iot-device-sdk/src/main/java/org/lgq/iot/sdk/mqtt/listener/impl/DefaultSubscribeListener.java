package org.lgq.iot.sdk.mqtt.listener.impl;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.lgq.iot.sdk.mqtt.listener.CustomSubscribeListener;

@Slf4j
public class DefaultSubscribeListener implements CustomSubscribeListener {

    @Override
    public void onSuccess(IMqttToken iMqttToken) {
        log.info("Subscribe mqtt topic success, topic={} .", iMqttToken.getTopics()[0]);
    }

    @Override
    public void onFailure(IMqttToken iMqttToken, Throwable e) {
        log.error("Subscribe mqtt topic fail, topic={} .", iMqttToken.getTopics()[0], e);
    }
}
