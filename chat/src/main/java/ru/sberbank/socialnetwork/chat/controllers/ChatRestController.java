package ru.sberbank.socialnetwork.chat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.socialnetwork.chat.client.MessageClient;
import ru.sberbank.socialnetwork.chat.dto.ChatDto;
import ru.sberbank.socialnetwork.chat.dto.MessageDto;
import ru.sberbank.socialnetwork.chat.entities.Chat;
import ru.sberbank.socialnetwork.chat.services.ChatService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ChatRestController {

    private final ChatService chatService;
    private final MessageClient messageClient;

    @Autowired
    public ChatRestController(ChatService chatService, MessageClient messageClient) {
        this.chatService = chatService;
        this.messageClient = messageClient;
    }

    @PostMapping("/group/{groupId}/chat/create")
    public Long createChat(@RequestParam String creatorId,
                           @RequestParam String chatName,
                           @PathVariable Long groupId) {
        return chatService.createChat(creatorId, chatName, groupId).getId();
    }

    @GetMapping("/group/{groupId}/chat")
    public Collection<ChatDto> getGroupChats(@PathVariable Long groupId) {
        return chatService.getGroupChats(groupId)
                .stream()
                .map(ChatDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/group/{groupId}/chat/user/{uuid}")
    public Collection<ChatDto> getUserChats(@PathVariable Long groupId,
                                            @PathVariable String uuid) {
        return chatService.getUserChats(groupId, uuid)
                .stream()
                .map(ChatDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/chat/{chatId}")
    public ChatDto getChat(@PathVariable Long chatId) {
        return new ChatDto(chatService.getChat(chatId));
    }

    @PostMapping("/chat/{chatId}/rename")
    public ChatDto setChatName(@PathVariable Long chatId,
                               @RequestParam String chatName) {
        return new ChatDto(chatService.setChatName(chatId, chatName));
    }

    @PostMapping("/chat/{chatId}/user/{uuid}/add")
    public ChatDto addUser(@PathVariable Long chatId,
                           @PathVariable String uuid) {
        return new ChatDto(chatService.addUser(chatId, uuid));
    }

    @PostMapping("/chat/{chatId}/user/{uuid}/remove")
    public ChatDto removeUser(@PathVariable Long chatId,
                              @PathVariable String uuid) {
        return new ChatDto(chatService.removeUser(chatId, uuid));
    }

    @GetMapping("/chat/{chatId}/user")
    public Collection<String> getChatUserUuids(@PathVariable Long chatId) {
        return chatService.getChatUserUuids(chatId);
    }

    @PostMapping("/chat/{chatId}/remove")
    public void removeChat(@PathVariable Long chatId) {
        chatService.removeChat(chatId);
    }
}
