package org.lgq.iot.sdk.chatgpt.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lgq.iot.sdk.chatgpt.util.SseHelper;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * sse
 *
 * @author plexpt
 */
@Slf4j
@RequiredArgsConstructor
public class SseStreamListener extends AbstractStreamListener {

    final SseEmitter sseEmitter;


    @Override
    public void onMsg(String message) {
        SseHelper.send(sseEmitter, message);
    }

    @Override
    public void onError(Throwable throwable, String response) {
        SseHelper.complete(sseEmitter);
    }

}
