package org.lgq.iot.sdk.mqtt.client;

import java.util.ArrayList;
import java.util.List;

public class TopicManager {

    // 上行：消息上报
    private String messagesUpTopic;
    // 下行：消息下发
    private String messagesDownTopic;

    // 下行：平台命令下发
    private String commandsDownTopic;
    // 上行：响应平台命令下发
    private String commandsRespTopic;

    // 上行：设备上报属性
    private String propertiesReportTopic;
    // 上行：设备上报子设备属性
    private String gatewaySubDevicePropertiesReportTopic;

    // 下行：平台设置设备属性
    private String propertiesSetTopic;
    // 上行：平台设置设备属性的响应
    private String propertiesSetRespTopic;

    // 下行：平台查询设备属性
    private String propertiesGetTopic;
    // 上行：平台查询设备属性的响应
    private String propertiesGetRespTopic;

    // 上行：设备主动获取平台的设备影子数据
    private String shadowGetTopic;
    // 下行：设备主动获取平台的设备影子数据的响应
    private String shadowGetRespTopic;

    // 上行：设备事件上报
    private String eventsUpTopic;
    // 下行：平台事件下发
    private String eventsDownTopic;

    private List<String> DEFAULT_DOWN_TOPICS = new ArrayList<>();

    public TopicManager(String deviceId) {
        init(deviceId);
    }

    private void init(String deviceId) {
        String topicPrefix = "$oc/devices/" + deviceId + "/sys/";
        this.messagesUpTopic = topicPrefix + "messages/up";
        this.messagesDownTopic = topicPrefix + "messages/down";
        this.commandsDownTopic = topicPrefix + "commands/#";
        this.commandsRespTopic = topicPrefix + "commands/response/request_id=";
        this.propertiesReportTopic = topicPrefix + "properties/report";
        this.gatewaySubDevicePropertiesReportTopic = topicPrefix + "gateway/sub_devices/properties/report";
        this.propertiesSetTopic = topicPrefix + "properties/set/#";
        this.propertiesSetRespTopic = topicPrefix + "properties/set/response/request_id=";
        this.propertiesGetTopic = topicPrefix + "properties/get/#";
        this.propertiesGetRespTopic = topicPrefix + "properties/get/response/request_id=";
        this.shadowGetTopic = topicPrefix + "shadow/get/request_id=";
        this.shadowGetRespTopic = topicPrefix + "shadow/get/response/#";
        this.eventsUpTopic = topicPrefix + "events/up";
        this.eventsDownTopic = topicPrefix + "events/down";
        DEFAULT_DOWN_TOPICS.add(messagesDownTopic);
        DEFAULT_DOWN_TOPICS.add(commandsDownTopic);
        DEFAULT_DOWN_TOPICS.add(propertiesSetTopic);
        DEFAULT_DOWN_TOPICS.add(propertiesGetTopic);
        DEFAULT_DOWN_TOPICS.add(shadowGetRespTopic);
        DEFAULT_DOWN_TOPICS.add(eventsDownTopic);
    }

    public String getMessagesUpTopic() {
        return messagesUpTopic;
    }

    public String getMessagesDownTopic() {
        return messagesDownTopic;
    }

    public String getCommandsDownTopic() {
        return commandsDownTopic;
    }

    public String getCommandsRespTopic(String requestId) {
        return commandsRespTopic + requestId;
    }

    public String getPropertiesReportTopic() {
        return propertiesReportTopic;
    }

    public String getGatewaySubDevicePropertiesReportTopic() {
        return gatewaySubDevicePropertiesReportTopic;
    }

    public String getPropertiesSetTopic() {
        return propertiesSetTopic;
    }

    public String getPropertiesSetRespTopic(String requestId) {
        return propertiesSetRespTopic + requestId;
    }

    public String getPropertiesGetTopic() {
        return propertiesGetTopic;
    }

    public String getPropertiesGetRespTopic(String requestId) {
        return propertiesGetRespTopic + requestId;
    }

    public String getShadowGetTopic(String requestId) {
        return shadowGetTopic + requestId;
    }

    public String getShadowGetRespTopic() {
        return shadowGetRespTopic;
    }

    public String getEventsUpTopic() {
        return eventsUpTopic;
    }

    public String getEventsDownTopic() {
        return eventsDownTopic;
    }

    public List<String> getDEFAULT_DOWN_TOPICS() {
        return DEFAULT_DOWN_TOPICS;
    }
}
