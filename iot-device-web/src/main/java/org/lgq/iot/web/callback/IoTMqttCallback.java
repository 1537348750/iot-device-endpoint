package org.lgq.iot.web.callback;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.lgq.iot.sdk.mqtt.callback.DefaultMqttCallback;
import org.lgq.iot.sdk.mqtt.client.MqttClient;
import org.lgq.iot.sdk.mqtt.listener.CustomResponseListener;

public class IoTMqttCallback extends DefaultMqttCallback {


    public IoTMqttCallback(MqttClient client, CustomResponseListener responseListener) {
        super(client, responseListener);
    }

    public IoTMqttCallback(MqttClient client) {
        super(client);
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        super.messageArrived(topic, message);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        super.deliveryComplete(iMqttDeliveryToken);
    }

    @Override
    public void connectComplete(boolean reconnect, String serviceURI) {
        super.connectComplete(reconnect, serviceURI);
    }

    @Override
    public void connectionLost(Throwable throwable) {
        super.connectionLost(throwable);
    }
}
