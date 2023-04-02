package org.lgq.iot.sdk.mqtt.listener;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.lgq.iot.sdk.mqtt.client.MqttClient;

import java.security.SecureRandom;

@Slf4j
public class DefaultConnectListener implements IMqttActionListener {

    private MqttClient client;
    private Boolean isSSL;

    private static int retryTimes = 0;
    private SecureRandom random = new SecureRandom();

    public DefaultConnectListener(MqttClient client, Boolean isSSL) {
        this.client = client;
        this.isSSL = isSSL;
    }

    @Override
    public void onSuccess(IMqttToken iMqttToken) {
        retryTimes = 0;
        log.info("Mqtt connect success, clientId={}", iMqttToken.getClient().getClientId());
    }

    @Override
    public void onFailure(IMqttToken iMqttToken, Throwable throwable) {
        log.error("Mqtt connect fail, clientId={}", iMqttToken.getClient().getClientId(), throwable);

        //退避重连
        long waitTimeUntilNextRetry = getNextRetryWaitTime();
        log.warn("Retreat reconnection--{}, clientId={}", retryTimes, iMqttToken.getClient().getClientId());
        try {
            Thread.sleep(waitTimeUntilNextRetry);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        retryTimes++;
        client.connect(isSSL);
    }

    private long getNextRetryWaitTime() {
        long defaultBackoff = 1000;
        int lowBound = (int) (defaultBackoff * 0.8);
        int highBound = (int) (defaultBackoff * 1.2);
        long randomBackOff = random.nextInt(highBound - lowBound);
        long backOffWithJitter = (int) (Math.pow(2.0, (double) retryTimes)) * (randomBackOff + lowBound);
        long minBackoff = 1000;
        //30 seconds
        long maxBackoff = 30 * 1000;
        return (int) (minBackoff + backOffWithJitter) > maxBackoff ? maxBackoff : (minBackoff + backOffWithJitter);
    }
}
