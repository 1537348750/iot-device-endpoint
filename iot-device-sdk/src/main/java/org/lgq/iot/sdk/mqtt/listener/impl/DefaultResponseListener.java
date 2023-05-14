package org.lgq.iot.sdk.mqtt.listener.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.lgq.iot.sdk.mqtt.client.MqttClient;
import org.lgq.iot.sdk.mqtt.dto.CommandsRespDTO;
import org.lgq.iot.sdk.mqtt.listener.CustomResponseListener;
import org.lgq.iot.sdk.mqtt.utils.BeanUtil;
import org.lgq.iot.sdk.mqtt.utils.ExceptionUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class DefaultResponseListener implements CustomResponseListener {

    @Override
    public void response(MqttClient client, String topic, MqttMessage mqttMessage) {
        if (topic != null && topic.startsWith("$oc/devices/") ) {
            if (topic.contains("/sys/commands/request_id=")) {
                log.debug("commands response...");
                autoCommandResponse(client, topic, mqttMessage);
            }
            if (topic.contains("/sys/properties/set")) {
                log.debug("properties set response...");
                propertiesSetResp(client, topic, mqttMessage);
            }
        }
    }

    private void autoCommandResponse(MqttClient client, String topic, MqttMessage mqttMessage) {
        try {
            // 下发的命令内容在MqttMessage对象中，根据不同的命令让设备去执行不同的任务
            // TODO 实现根据下发的命令执行自定义任务...
            CommandsRespDTO commandsResp = new CommandsRespDTO();
            commandsResp.setParas(Collections.singletonMap("result", "success"));
            client.commandsResp(BeanUtil.pojoToJson(commandsResp), getRequestId(topic), null, null);
        } catch (MqttException e) {
            log.error("command response fail, e = {}", ExceptionUtil.getBriefStackTrace(e));
        } catch (JsonProcessingException e) {
            log.error("json process fail, e = {}", ExceptionUtil.getBriefStackTrace(e));
        }
    }

    private void propertiesSetResp(MqttClient client, String topic, MqttMessage mqttMessage) {
        Map<String, Object> map = new HashMap<>(4);
        map.put("result_code", 0);
        map.put("result_desc", "success");
        try {
            client.propertiesSetResp(BeanUtil.pojoToJson(map), getRequestId(topic), null, null);
        } catch (MqttException e) {
            log.error("properties set response fail, e = {}", ExceptionUtil.getBriefStackTrace(e));
        } catch (JsonProcessingException e) {
            log.error("json process fail, e = {}", ExceptionUtil.getBriefStackTrace(e));
        }
    }

    private String getRequestId(String topic) {
        return topic.split("request_id=")[1];
    }
}
