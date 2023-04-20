package org.lgq.iot.sdk.mqtt.client;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.lgq.iot.sdk.mqtt.callback.CustomMqttCallback;
import org.lgq.iot.sdk.mqtt.callback.DefaultMqttCallback;
import org.lgq.iot.sdk.mqtt.listener.*;
import org.lgq.iot.sdk.mqtt.listener.impl.DefaultConnectListener;
import org.lgq.iot.sdk.mqtt.listener.impl.DefaultPublishListener;
import org.lgq.iot.sdk.mqtt.listener.impl.DefaultSubscribeListener;
import org.lgq.iot.sdk.mqtt.utils.MqttUtil;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

@Slf4j
public class MqttClient {

    public static final Integer DEFAULT_QOS = 1;

    private String serverIp;
    private final String deviceId;
    private final String secret;
    private Integer qosLevel;

    private TopicManager topicManager;
    private MqttAsyncClient asyncClient;

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
        this(serviceIp, deviceId, secret, DEFAULT_QOS);
    }

    public TopicManager getTopicManager() {
        return this.topicManager;
    }

    /**
     * mqtt建链
     * @param isSSL true:url=ssl://${serverIp}:8883, false:url=tcp://${serverIp}:1883
     */
    public void connect(boolean isSSL) {
        this.connect(isSSL, null, null);
    }

    public void connect(boolean isSSL, CustomMqttCallback callback) {
        this.connect(isSSL, callback, null);
    }

    public void connect(boolean isSSL, CustomConnectListener listener) {
        this.connect(isSSL, null, listener);
    }

    public void connect(boolean isSSL, CustomMqttCallback callback, CustomConnectListener listener) {
        String url;
        if (isSSL) {
            url = "ssl://" + serverIp + ":" + 8883; //mqtts连接
        } else {
            url = "tcp://" + serverIp + ":" + 1883; //mqtt连接
        }
        try {
            MqttConnectOptions options = new MqttConnectOptions();
            if (isSSL) {
                options.setSocketFactory(MqttUtil.getOptionSocketFactory(Objects.requireNonNull(
                        MqttClient.class.getClassLoader().getResource("cn-north-4-device-client-rootcert.jks")
                ).getPath()));
                options.setHttpsHostnameVerificationEnabled(false);
            }
            options.setCleanSession(false);
            options.setKeepAliveInterval(120);
            options.setConnectionTimeout(5000);
            options.setAutomaticReconnect(true);
            options.setUserName(deviceId);
            options.setPassword(MqttUtil.getPassword(secret).toCharArray());

            log.info("Start mqtt connect, url={}", url);
            asyncClient = new MqttAsyncClient(url, MqttUtil.getClientId(deviceId), new MemoryPersistence());
            callback = (callback != null) ? callback : new DefaultMqttCallback(this);
            asyncClient.setCallback(callback);
            listener = (listener != null) ? listener : new DefaultConnectListener(this, isSSL);
            asyncClient.connect(options, null, listener);
        } catch (Exception e) {
            log.error("connect fail, e={}", e.getLocalizedMessage());
        }
    }

    public boolean isConnect() {
        if (asyncClient == null) {
            return false;
        }
        return asyncClient.isConnected();
    }

    /**
     * 关闭客户端，设备下线
     */
    public void close() {
        try {
            if (asyncClient == null) {
                return;
            }
            asyncClient.disconnect();
            asyncClient.close();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * 订阅默认下行topic
     */
    public void subScribeDefaultTopics() {
        this.subScribeTopics(topicManager.getDEFAULT_DOWN_TOPICS(), null);
    }

    /**
     * 订阅下行topic
     * @param topics 下行topic，自定义topic的列表
     */
    public void subScribeTopics(List<String> topics) {
        this.subScribeTopics(topics, null);
    }

    /**
     * 订阅下行topic
     * @param topics   下行topic，自定义topic
     * @param listener 订阅topic订阅的结果监听器
     */
    public void subScribeTopics(List<String> topics, CustomSubscribeListener listener) {
        if (topics == null || topics.size() == 0) return;
        try {
            listener = (listener != null) ? listener : new DefaultSubscribeListener();
            for (String topic : topics) {
                asyncClient.subscribe(topic, qosLevel, null, listener);
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * 消息上报
     * @param message 上报的消息内容，可以是字符串，也可以是json字符串
     */
    public IMqttDeliveryToken messagesUp(String message, CustomPublishListener listener) throws MqttException {
        return this.publish(getPayload(message), topicManager.getMessagesUpTopic(), null, listener);
    }

    /**
     * 消息上报
     */
    public IMqttDeliveryToken messagesUp(String message, Integer qosLv, CustomPublishListener listener) throws MqttException {
        return this.publish(getPayload(message), topicManager.getMessagesUpTopic(), qosLv, listener);
    }

    /**
     * 发布
     */
    public IMqttDeliveryToken publishData(String data, String topic, Integer qosLv, CustomPublishListener listener) throws MqttException {
        return this.publish(getPayload(data), topic, qosLv, listener);
    }

    /**
     * 发布二进制数据
     */
    public IMqttDeliveryToken publishBinaryData(byte[] payload, String topic, Integer qosLv, CustomPublishListener listener) throws MqttException {
        return this.publish(payload, topic, qosLv, listener);
    }

    /**
     * 发布消息
     * @param payload  发布的消息体
     * @param topic    发布的topic
     * @param qosLv    QOS等级，默认QOS=1
     * @param listener 消息发布结果的监听器
     */
    private IMqttDeliveryToken publish(byte[] payload, String topic, Integer qosLv, IMqttActionListener listener) throws MqttException {
        MqttMessage message = new MqttMessage(payload);
        int qos = (qosLv == null) ? qosLevel : (qosLv == 0 || qosLv == 1 || qosLv == 2) ? qosLv : qosLevel;
        listener = (listener != null) ? listener : new DefaultPublishListener(payload);
        return asyncClient.publish(topic, message, qos, listener);
    }

    private byte[] getPayload(String string) {
        return string.getBytes(StandardCharsets.UTF_8);
    }
}
