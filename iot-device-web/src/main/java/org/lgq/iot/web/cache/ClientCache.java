package org.lgq.iot.web.cache;

import org.lgq.iot.sdk.mqtt.client.MqttClient;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClientCache {

    private static Map<String, MqttClient> DEVICE_CACHE = new ConcurrentHashMap<>(1000);

    public static MqttClient cacheClient(String deviceId, MqttClient client) {
        return DEVICE_CACHE.put(deviceId, client);
    }

    public static MqttClient getClient(String deviceId) {
        return DEVICE_CACHE.get(deviceId);
    }

    public static MqttClient removeClient(String deviceId) {
        getClient(deviceId).close();
        return DEVICE_CACHE.remove(deviceId);
    }
}
