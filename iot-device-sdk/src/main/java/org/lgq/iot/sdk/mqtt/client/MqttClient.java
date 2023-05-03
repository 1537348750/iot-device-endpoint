package org.lgq.iot.sdk.mqtt.client;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.lgq.iot.sdk.mqtt.callback.CustomMqttCallback;
import org.lgq.iot.sdk.mqtt.callback.DefaultMqttCallback;
import org.lgq.iot.sdk.mqtt.listener.CustomConnectListener;
import org.lgq.iot.sdk.mqtt.listener.CustomPublishListener;
import org.lgq.iot.sdk.mqtt.listener.CustomSubscribeListener;
import org.lgq.iot.sdk.mqtt.listener.impl.DefaultConnectListener;
import org.lgq.iot.sdk.mqtt.listener.impl.DefaultPublishListener;
import org.lgq.iot.sdk.mqtt.listener.impl.DefaultSubscribeListener;
import org.lgq.iot.sdk.mqtt.utils.ExceptionUtil;
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
        this.qosLevel = (qosLevel == null) ? DEFAULT_QOS : (qosLevel == 0 || qosLevel == 1 || qosLevel == 2) ? qosLevel : DEFAULT_QOS;
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
    public void connect(Boolean isSSL) {
        this.connect(isSSL, null, null);
    }

    public void connect(Boolean isSSL, CustomMqttCallback callback) {
        this.connect(isSSL, callback, null);
    }

    public void connect(Boolean isSSL, CustomConnectListener listener) {
        this.connect(isSSL, null, listener);
    }

    public void connect(Boolean isSSL, CustomMqttCallback callback, CustomConnectListener listener) {
        String url;
        boolean ssl = (isSSL == null) ? false : isSSL;
        if (ssl) {
            url = "ssl://" + serverIp + ":" + 8883; //mqtts连接
        } else {
            url = "tcp://" + serverIp + ":" + 1883; //mqtt连接
        }
        try {
            MqttConnectOptions options = new MqttConnectOptions();
            if (ssl) {
                String certFileName = "cn-north-4-device-client-rootcert.jks";
                options.setSocketFactory(MqttUtil.getOptionSocketFactory(Objects.requireNonNull(MqttClient.class.getClassLoader().getResource(certFileName)).getPath()));
                options.setHttpsHostnameVerificationEnabled(false);
            }
            DeviceInfo deviceInfo = MqttUtil.getDeviceInfo(deviceId, secret);
            options.setCleanSession(false);
            options.setKeepAliveInterval(120);
            options.setConnectionTimeout(5000);
            options.setAutomaticReconnect(true);
            options.setUserName(deviceId);
            options.setPassword(deviceInfo.getPassword().toCharArray());

            log.info("Start mqtt connect, url={}", url);
            asyncClient = new MqttAsyncClient(url, deviceInfo.getClientId(), new MemoryPersistence());
            callback = (callback != null) ? callback : new DefaultMqttCallback(this);
            asyncClient.setCallback(callback);
            listener = (listener != null) ? listener : new DefaultConnectListener(this, ssl);
            asyncClient.connect(options, null, listener);
        } catch (Exception e) {
            log.error("connect fail, e={}", ExceptionUtil.getBriefStackTrace(e));
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

    public Integer getQosLevel() {
        return qosLevel;
    }

    /**
     * 订阅默认下行topic
     */
    public void subScribeDefaultTopics() {
        if (topicManager.getDEFAULT_DOWN_TOPICS().size() == 0) {
            return;
        }
        this.subScribeTopics(topicManager.getDEFAULT_DOWN_TOPICS(), null);
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
     */
    public IMqttDeliveryToken messagesUp(String message, Integer qosLv, CustomPublishListener listener) throws MqttException {
        return this.publish(getPayload(message), topicManager.getMessagesUpTopic(), qosLv, listener);
    }

    /**
     * 属性上报
     * @param properties
     */
    public IMqttDeliveryToken propertiesReport(String properties, Integer qosLv, CustomPublishListener listener) throws MqttException {
        return this.publish(getPayload(properties), topicManager.getPropertiesReportTopic(), qosLv, listener);
    }

    /**
     * 网关子设备属性上报
     * @param properties
     */
    public IMqttDeliveryToken gatewaySubDevicePropertiesReport(String properties, Integer qosLv, CustomPublishListener listener)  throws MqttException {
        return this.publish(getPayload(properties), topicManager.getGatewaySubDevicePropertiesReportTopic(), qosLv, listener);
    }

    /**
     * 事件上报
     */
    public IMqttDeliveryToken eventUp(String data, Integer qosLv, CustomPublishListener listener) throws MqttException {
        return this.publish(getPayload(data), topicManager.getEventsUpTopic(), qosLv, listener);
    }

    /**
     * 响应命令下发
     * @param data      命令响应的内容
     * @param requestId 命令下发携带的request_id
     * @param qosLv
     * @param listener
     * @return
     * @throws MqttException
     */
    public IMqttDeliveryToken commandsResp(String data, String requestId, Integer qosLv, CustomPublishListener listener) throws MqttException {
        return this.publish(getPayload(data), topicManager.getCommandsRespTopic(requestId), qosLv, listener);
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
