package dev.zopad.chatserver.service;

import dev.zopad.model.Message;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService {

    private List<Message> allMessages = new ArrayList<>();

    public void addMessage(Message message) {
        allMessages.add(message);
    }

    public List<Message> getAllMessages() {
        return allMessages;
    }

}
