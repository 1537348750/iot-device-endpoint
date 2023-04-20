package org.lgq.iot.chatgpt.entity.chat;


import lombok.Data;
import org.lgq.iot.chatgpt.entity.billing.Usage;

import java.util.List;

/**
 * chat答案类
 *
 * @author plexpt
 */
@Data
public class ChatCompletionResponse {
    private String id;
    private String object;
    private long created;
    private String model;
    private List<ChatChoice> choices;
    private Usage usage;
}
