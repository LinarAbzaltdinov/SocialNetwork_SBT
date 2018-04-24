package ru.sberbank.socialnetwork.webui.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.socialnetwork.webui.models.Message;
import ru.sberbank.socialnetwork.webui.services.ChatService;

@Controller
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    ChatService chatService;

    @GetMapping("/{id}")
    public String showChats(@PathVariable("id") String chatId) {
        return "chats";
    }

    @PostMapping("/{id}/sendMessage")
    public @ResponseBody ResponseEntity sendMessage(@RequestParam String messageText,
                                      @PathVariable("id") String chatId) {
        ResponseEntity result = chatService.createMessage(chatId, messageText);
        return result;
    }
}
