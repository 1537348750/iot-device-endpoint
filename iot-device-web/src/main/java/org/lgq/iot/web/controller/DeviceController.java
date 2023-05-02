package org.lgq.iot.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.lgq.iot.sdk.mqtt.utils.BeanUtil;
import org.lgq.iot.web.dto.DeviceInfoDto;
import org.lgq.iot.web.dto.SubscribeTopics;
import org.lgq.iot.web.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "/v1/device")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @PostMapping("/online")
    public String online(@RequestBody DeviceInfoDto deviceInfoDto) {
        deviceService.online(deviceInfoDto);
        return "device connect success !";
    }

    @DeleteMapping("/{device_id}/offline")
    public String offline(@PathVariable("device_id") String deviceId) {
        deviceService.offline(deviceId);
        return "device offline !";
    }

    @PostMapping("/subscribe-topics")
    public String subscribeTopics(@RequestBody SubscribeTopics subScribeTopics) {
        deviceService.subscribeTopics(subScribeTopics);
        return "subscribe topics success !";
    }

    @PostMapping("/{device_id}/message-up")
    public String messagesUp(@RequestBody Map<String, Object> map, @PathVariable("device_id") String deviceId) {
        String json;
        try {
            json = BeanUtil.pojoToJson(map);
            deviceService.messageUp(json, deviceId);
            return "message up success !";
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/{device_id}/event-up")
    public String eventUp(@RequestBody Map<String, Object> map, @PathVariable("device_id") String deviceId) {
        try {
            String json = BeanUtil.pojoToJson(map);
            deviceService.eventUp(json, deviceId);
            return "event up success !";
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/{device_id}/properties-report")
    public String propertiesReport(@RequestBody Map<String, Object> map, @PathVariable("device_id") String deviceId) {
        try {
            String json = BeanUtil.pojoToJson(map);
            deviceService.propertiesReport(json, deviceId);
            return "properties report success !";
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/{device_id}/gateway-subdevice/properties-report")
    public String gatewaySubDevicePropertiesReport(@RequestBody Map<String, Object> map, @PathVariable("device_id") String deviceId) {
        try {
            String json = BeanUtil.pojoToJson(map);
            deviceService.gatewaySubDevicePropertiesReport(json, deviceId);
            return "gateway subDevice properties report success !";
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
