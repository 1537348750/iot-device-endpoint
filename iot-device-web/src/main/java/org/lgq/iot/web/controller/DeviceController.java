package org.lgq.iot.web.controller;

import org.lgq.iot.web.dto.DeviceInfoDto;
import org.lgq.iot.web.dto.MessageUpDto;
import org.lgq.iot.web.dto.SubscribeTopics;
import org.lgq.iot.web.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/offline/{device_id}")
    public String offline(@PathVariable("device_id") String deviceId) {
        deviceService.offline(deviceId);
        return "device offline !";
    }

    @PostMapping("/message-up")
    public String messagesUp(@RequestBody MessageUpDto messageUpDto) {
        deviceService.messageUp(messageUpDto);
        return "message up success !";
    }

    @PostMapping("/subscribe-topics")
    public String subscribeTopics(@RequestBody SubscribeTopics subScribeTopics) {
        deviceService.subscribeTopics(subScribeTopics);
        return "subscribe topics success !";
    }
}
