package org.lgq.iot.sdk.mqtt.client;


/**
 * 暂时不使用这个类
 */
@Deprecated
public class IoTDevice {

    private MqttClient client;

    public IoTDevice(String serviceIp, String deviceId, String secret, Integer qosLevel) {
        init(serviceIp, deviceId, secret, qosLevel);
    }

    public IoTDevice(String serviceIp, String deviceId, String secret) {
        this(serviceIp, deviceId, secret, 1);
    }

    private void init(String serviceIp, String deviceId, String secret, Integer qosLevel) {
        client = new MqttClient(serviceIp, deviceId, secret, qosLevel);
    }

    public MqttClient getClient() {
        return client;
    }

    public void close() {
        this.client.close();
        this.client = null;
    }

}
