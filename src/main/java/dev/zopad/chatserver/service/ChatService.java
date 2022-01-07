package dev.zopad.chatserver.service;

import dev.zopad.model.Message;
import org.springframework.stereotype.Service;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class ChatService {

    private final UnicastProcessor<Message> publisher;
    private final Flux<Message> messages;
    private List<Message> allMessages = new ArrayList<>();

    public ChatService(UnicastProcessor<Message> publisher, Flux<Message> messages) {
        this.publisher = publisher;
        this.messages = messages;
    }

    public Disposable subscribe(Consumer<? super Message> consumer) {
        return messages.subscribe(consumer);
    }

    public void sendMessage(Message message) {
        publisher.onNext(message);
        allMessages.add(message);
    }

    //N.B: this is not reactive, it's just for the REST getMessages endpoint
    public List<Message> getAllMessages() {
        return allMessages;
    }
}
