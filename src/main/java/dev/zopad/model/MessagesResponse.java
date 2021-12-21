package dev.zopad.model;

import java.util.List;

public class MessagesResponse {

    private List<Message> messages;

    public MessagesResponse(List<Message> messages) {
        this.messages = messages;
    }

    public List<Message> getMessages() {
        return messages;
    }
}
