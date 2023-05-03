package org.lgq.iot.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.lgq.iot.sdk.mqtt.utils.BeanUtil;
import org.lgq.iot.web.dto.DeviceInfoDto;
import org.lgq.iot.web.dto.SubscribeTopics;
import org.lgq.iot.web.result.Result;
import org.lgq.iot.web.result.IotCode;
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
    public Result online(@RequestBody DeviceInfoDto deviceInfoDto) {
        deviceService.online(deviceInfoDto);
        return Result.success(IotCode.SUCCESS).message("device connect success !");
    }

    @DeleteMapping("/{device_id}/offline")
    public Result offline(@PathVariable("device_id") String deviceId) {
        deviceService.offline(deviceId);
        return Result.success(IotCode.SUCCESS).message("device offline !");
    }

    @PostMapping("/subscribe-topics")
    public Result subscribeTopics(@RequestBody SubscribeTopics subScribeTopics) {
        deviceService.subscribeTopics(subScribeTopics);
        return Result.success(IotCode.SUCCESS).message("subscribe topics success !");
    }

    @PostMapping("/{device_id}/message-up")
    public Result messagesUp(@RequestBody Map<String, Object> map, @PathVariable("device_id") String deviceId) {
        String json;
        try {
            json = BeanUtil.pojoToJson(map);
            deviceService.messageUp(json, deviceId);
            return Result.success(IotCode.SUCCESS).message("message up success !");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/{device_id}/event-up")
    public Result eventUp(@RequestBody Map<String, Object> map, @PathVariable("device_id") String deviceId) {
        try {
            String json = BeanUtil.pojoToJson(map);
            deviceService.eventUp(json, deviceId);
            return Result.success(IotCode.SUCCESS).message("event up success !");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/{device_id}/properties-report")
    public Result propertiesReport(@RequestBody Map<String, Object> map, @PathVariable("device_id") String deviceId) {
        try {
            String json = BeanUtil.pojoToJson(map);
            deviceService.propertiesReport(json, deviceId);
            return Result.success(IotCode.SUCCESS).message("properties report success !");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/{device_id}/gateway-subdevice/properties-report")
    public Result gatewaySubDevicePropertiesReport(@RequestBody Map<String, Object> map, @PathVariable("device_id") String deviceId) {
        try {
            String json = BeanUtil.pojoToJson(map);
            deviceService.gatewaySubDevicePropertiesReport(json, deviceId);
            return Result.success(IotCode.SUCCESS).message("gateway subDevice properties report success !");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
