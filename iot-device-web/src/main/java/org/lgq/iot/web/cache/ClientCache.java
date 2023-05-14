package org.lgq.iot.web.cache;

import lombok.extern.slf4j.Slf4j;
import org.lgq.iot.sdk.mqtt.client.MqttClient;
import org.lgq.iot.sdk.mqtt.utils.ExceptionUtil;
import org.lgq.iot.web.config.IPConfig;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class ClientCache {

    private static Map<String, MqttClient> DEVICE_CACHE = new ConcurrentHashMap<>(10000, 1f);

    public static MqttClient cacheClient(String deviceId, MqttClient client) {
        return DEVICE_CACHE.put(deviceId, client);
    }

    public static MqttClient getClient(String deviceId) {
        return DEVICE_CACHE.get(deviceId);
    }

    public static void removeClient(String deviceId) {
        try {
            MqttClient client = getClient(deviceId);
            if (client != null) {
                client.close();
                DEVICE_CACHE.remove(deviceId);
                log.info("Mqtt device offline, deviceId={}, containerIp={}", deviceId, IPConfig.getContainerIp());
            }
        } catch (Exception e) {
            log.error("close client fail, e = {}", ExceptionUtil.getBriefStackTrace(e));
        }
    }
}
