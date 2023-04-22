package org.lgq.iot.sdk.mqtt.listener;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.lgq.iot.sdk.mqtt.client.MqttClient;

/**
 * 平台下发监听器
 */
@FunctionalInterface
public interface CustomResponseListener {

    /**
     * 接收到物联网平台的下发，比如消息下发，事件下发，命令下发，属性设置等下发topic，通过此方法执行不同的操作和应答物联网平台
     * @param client  设备客户端
     * @param topic   平台下发的topic
     * @param message 下发的消息内容
     */
    void response(MqttClient client, String topic, MqttMessage message);
}
