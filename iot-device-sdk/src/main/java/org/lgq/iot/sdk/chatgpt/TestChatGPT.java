package org.lgq.iot.sdk.chatgpt;

import org.lgq.iot.sdk.chatgpt.api.Api;
import org.lgq.iot.sdk.chatgpt.util.ApiKeyUtil;
import org.lgq.iot.sdk.chatgpt.util.ProxyUtil;

public class TestChatGPT {

    public static void main(String[] args) {
        String your_question = "用Java写一个MQTTS服务端";
        ChatGPT chatGPT = ChatGPT.builder()
                .apiKey(ApiKeyUtil.getApiKey())
                .proxy(ProxyUtil.getLocalProxy())
                .apiHost(Api.DEFAULT_API_HOST)
                .build()
                .init();
        //String result = chatGPT.chat(your_question);
        //System.out.println(result);
    }
}
