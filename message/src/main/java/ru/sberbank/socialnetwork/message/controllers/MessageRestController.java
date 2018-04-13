package ru.sberbank.socialnetwork.message.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.socialnetwork.message.services.MessageService;
import ru.sberbank.socialnetwork.message.entities.Message;

@RestController
public class MessageRestController {
    @Autowired
    private MessageService messageService;

    @GetMapping("/msg/{id}")
    public Message showMessage(@PathVariable String id) {
        Message foundMessage = messageService.getMessage(id);
        return foundMessage;
    }

    @PostMapping("/msg")
    public String createMessage(@RequestParam String messageContent) {
        Message createdMessage = messageService.createMessage(messageContent);
        return createdMessage.getId()+"";
    }
}
