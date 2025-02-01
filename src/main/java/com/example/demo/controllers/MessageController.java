package com.example.demo.controllers;

import com.example.demo.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {
    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/send/{senderAuthToken}/{receiverAuthToken}/{body}")
    public String sendMessage(
            @PathVariable Long senderAuthToken,
            @PathVariable Long receiverAuthToken,
            @PathVariable String body) {
        return this.messageService.sendMessage(senderAuthToken, receiverAuthToken, body);
    }

    @GetMapping("/inbox/{authToken}")
    public List<String> showInbox(@PathVariable Long authToken) {
        return this.messageService.showInbox(authToken);
    }
    @GetMapping("read/{id}")
    public String readMessage(@PathVariable Long id) {
        return this.messageService.openMessage(id);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteMessage(@PathVariable Long id){
        return this.messageService.deleteMessage(id);
    }



}
