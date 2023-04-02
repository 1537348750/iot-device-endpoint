package org.lgq.iot.sdk.chatgpt.exception;

/**
 * Custom exception class for chat-related errors
 *
 * @author plexpt
 */
public class ChatGptException extends RuntimeException {


    /**
     * Constructs a new ChatException with the specified detail message.
     *
     * @param msg the detail message (which is saved for later retrieval by the getMessage() method)
     */
    public ChatGptException(String msg) {
        super(msg);
    }


}
