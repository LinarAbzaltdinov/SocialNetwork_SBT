package ru.sberbank.socialnetwork.chat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.socialnetwork.chat.entities.Chat;
import ru.sberbank.socialnetwork.chat.services.ChatService;

import java.util.Collection;

@RestController
public class ChatRestController {

    private final ChatService chatService;

    @Autowired
    public ChatRestController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/chat/{id}")
    public Chat getChat(@PathVariable Long id) {
        return chatService.getChat(id);
    }

    @PostMapping("/chat/new")
    public Chat createChat(@RequestParam String creatorId,
                           @RequestParam String chatName) {
        return chatService.createChat(creatorId, chatName);
    }

    @GetMapping("/chat/user/{uuid}")
    public Collection<Chat> getUserChats(@PathVariable String uuid) {
        return chatService.getUserChats(uuid);
    }

    @PostMapping("/chat/{id}/add_user/{uuid}")
    public Chat addUser(@PathVariable Long id,
                        @PathVariable String uuid) {
        return chatService.addUser(id, uuid);
    }

    @PostMapping("/chat/{id}/rm_user/{uuid}")
    public Chat removeUser(@PathVariable Long id,
                           @PathVariable String uuid) {
        return chatService.removeUser(id, uuid);
    }

    @PostMapping("/chat/{id}/send_msg")
    public Chat sendMessage(@PathVariable Long id,
                            @RequestParam String messageContent) {
        // send message
        String messageId = "";
        return chatService.sendMessage(id, messageId);
    }

    @GetMapping("/chat/{id}/users")
    public Collection<String> getChatUsers(@PathVariable Long id) {
        return chatService.getChatUsers(id);
    }
}
