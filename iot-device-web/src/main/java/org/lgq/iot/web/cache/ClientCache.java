package org.lgq.iot.web.cache;

import org.lgq.iot.sdk.mqtt.client.MqttClient;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClientCache {

    private static final Map<String, MqttClient> DEVICE_CACHE = new ConcurrentHashMap<>(1000);

    public static void cacheDeviceClient(String deviceId, MqttClient client) {
        DEVICE_CACHE.put(deviceId, client);
    }

    public static MqttClient getDeviceClient(String deviceId) {
        return DEVICE_CACHE.get(deviceId);
    }

    public static MqttClient removeDeviceClient(String deviceId) {
        getDeviceClient(deviceId).close();
        return DEVICE_CACHE.remove(deviceId);
    }
}
