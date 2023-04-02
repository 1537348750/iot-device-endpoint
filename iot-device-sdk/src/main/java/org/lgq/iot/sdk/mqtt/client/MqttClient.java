package org.lgq.iot.sdk.mqtt.client;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.lgq.iot.sdk.mqtt.listener.DefaultCallback;
import org.lgq.iot.sdk.mqtt.listener.DefaultConnectListener;
import org.lgq.iot.sdk.mqtt.listener.DefaultPublishListener;
import org.lgq.iot.sdk.mqtt.listener.DefaultSubscribeListener;
import org.lgq.iot.sdk.mqtt.utils.MqttUtil;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

@Slf4j
public class MqttClient {

    private String serverIp;
    private final String deviceId;
    private final String secret;
    private Integer qosLevel;
    private TopicManager topicManager;
    private MqttAsyncClient client;

    /**
     * @param serviceIp 物联网平台设备接入域名或者IP地址
     * @param deviceId  设备id
     * @param secret    设备秘钥
     * @param qosLevel  Mqtt qos等级
     */
    public MqttClient(String serviceIp, String deviceId, String secret, Integer qosLevel) {
        this.serverIp = serviceIp;
        this.deviceId = deviceId;
        this.secret = secret;
        this.qosLevel = qosLevel;
        this.topicManager = new TopicManager(deviceId);
    }

    public MqttClient(String serviceIp, String deviceId, String secret) {
        this(serviceIp, deviceId, secret, 1);
    }

    public TopicManager getTopicManager() {
        return this.topicManager;
    }

    /**
     * mqtt建链
     * @param isSSL true:url=ssl://${serverIp}:8883, false:url=tcp://${serverIp}:1883
     */
    public void connect(Boolean isSSL) {
        String url;
        boolean ssl = (isSSL == null) ? true : isSSL;
        if (ssl) {
            url = "ssl://" + serverIp + ":" + 8883; //mqtts连接
        } else {
            url = "tcp://" + serverIp + ":" + 1883; //mqtt连接
        }
        try {
            MqttConnectOptions options = new MqttConnectOptions();
            if (ssl) {
                options.setSocketFactory(MqttUtil.getOptionSocketFactory(Objects.requireNonNull(MqttClient.class.getClassLoader()
                        .getResource("cn-north-4-device-client-rootcert.jks")).getPath()));
                options.setHttpsHostnameVerificationEnabled(false);
            }
            options.setCleanSession(false);
            options.setKeepAliveInterval(120);
            options.setConnectionTimeout(5000);
            options.setAutomaticReconnect(true);
            options.setUserName(deviceId);
            options.setPassword(MqttUtil.getPassword(secret).toCharArray());
            log.info("Start mqtt connect, url={}", url);
            client = new MqttAsyncClient(url, MqttUtil.getClientId(deviceId), new MemoryPersistence());
            client.setCallback(new DefaultCallback(this));
            client.connect(options, null, new DefaultConnectListener(this, ssl));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("connect fail, e={}", e.getLocalizedMessage());
        }
    }

    /**
     * 关闭客户端，设备下线
     */
    public void close() {
        try {
            client.disconnect();
            client.close();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * 订阅默认下行topic
     */
    public void subScribeDefaultTopics() {
        this.subScribeTopics(
                topicManager.getDEFAULT_DOWN_TOPICS()
        );
    }

    /**
     * 订阅下行topic
     * @param topics 下行topic，自定义topic
     */
    public void subScribeTopics(List<String> topics) {
        if (topics == null || topics.size() == 0) return;
        try {
            DefaultSubscribeListener defaultSubscribeListener = new DefaultSubscribeListener();
            for (String topic : topics) {
                client.subscribe(topic, qosLevel, null, defaultSubscribeListener);
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * 消息上报
     * @param message message
     */
    public void messagesUp(String message, IMqttActionListener listener) {
        this.publish(getPayload(message), topicManager.getMessagesUpTopic(), null, listener);
    }

    /**
     * 消息上报
     */
    public void messagesUp(String message, Integer qosLv, IMqttActionListener listener) {
        this.publish(getPayload(message), topicManager.getMessagesUpTopic(), qosLv, listener);
    }

    /**
     * 发布二进制数据
     */
    public void publishBinaryData(byte[] payload, String topic, Integer qosLv, IMqttActionListener listener) {
        this.publish(payload, topic, qosLv, listener);
    }

    /**
     * 发布消息
     * @param payload 发布的消息体
     * @param topic   发布的topic
     * @param qosLv   QOS等级，默认QOS=1
     */
    private IMqttDeliveryToken publish(byte[] payload, String topic, Integer qosLv, IMqttActionListener listener) {
        MqttMessage message = new MqttMessage(payload);
        int qos = (qosLv == null) ? qosLevel : (qosLv == 0 || qosLv == 1 || qosLv == 2) ? qosLv : qosLevel;
        try {
            if (listener == null) {
                listener = new DefaultPublishListener(payload);
            }
            return client.publish(topic, message, qos, listener);
        } catch (MqttException e) {
            e.printStackTrace();
            return null;
        }
    }

    private byte[] getPayload(String string) {
        return string.getBytes(StandardCharsets.UTF_8);
    }
}
