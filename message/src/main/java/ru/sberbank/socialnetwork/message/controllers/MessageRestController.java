package ru.sberbank.socialnetwork.message.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.socialnetwork.message.dto.MessageDTO;
import ru.sberbank.socialnetwork.message.services.MessageService;
import ru.sberbank.socialnetwork.message.entities.Message;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageRestController {
    @Autowired
    private MessageService messageService;

    @GetMapping("/{id}")
    public ResponseEntity<MessageDTO> showMessage(@PathVariable String id) {
        MessageDTO foundMessage = messageService.getMessage(id);
        ResponseEntity<MessageDTO> messageResponeEntity;
        if (foundMessage == null) {
            messageResponeEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            messageResponeEntity = new ResponseEntity<>(foundMessage, HttpStatus.OK);
        }
        return messageResponeEntity;
    }

    @GetMapping("/of/chat/{chatId}")
    public List<MessageDTO> showMessagesOfChat(@PathVariable String chatId) {
        List<MessageDTO> chatMessages = messageService.getMessagesOfChat(chatId);
        return chatMessages;
    }

    @GetMapping("/of/user/{userId}")
    public List<MessageDTO> showMessageOfUser(@PathVariable String userId) {
        List<MessageDTO> userMessages = messageService.getMessagesOfUser(userId);
        return userMessages;
    }

    @PostMapping("/new")
    public String createMessage(@RequestParam String messageContent,
                                @RequestParam String userId,
                                @RequestParam String chatId) {
        MessageDTO createdMessage = messageService.createMessage(userId, chatId, messageContent);
        return createdMessage.getId();
    }
}
