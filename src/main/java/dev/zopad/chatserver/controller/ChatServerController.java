package dev.zopad.chatserver.controller;

import dev.zopad.chatserver.service.ChatService;
import dev.zopad.model.Message;
import dev.zopad.model.MessagesResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ChatServerController {

    private final ChatService service;

    public ChatServerController(ChatService service) {
        this.service = service;
    }

    @GetMapping("/getMessages")
    public ResponseEntity<MessagesResponse> getMessages() {
        return ResponseEntity.status(HttpStatus.OK).body(new MessagesResponse(service.getAllMessages()));
    }

    @PostMapping(value = "/sendMessage", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void sendMessage(@RequestBody Message message) {
        service.sendMessage(message);
    }
}
