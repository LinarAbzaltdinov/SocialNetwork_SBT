package ru.sberbank.socialnetwork.webui.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.socialnetwork.webui.models.Chat;
import ru.sberbank.socialnetwork.webui.models.Message;
import ru.sberbank.socialnetwork.webui.services.ChatService;

import java.util.List;

@Controller
@RequestMapping("/groups/{groupId}/chat")
public class ChatController {

    @Autowired
    ChatService chatService;

    @GetMapping("/{chatId}")
    @ResponseBody
    public List<Chat> showChats(@PathVariable("chatId") String chatId) {

        return null;
    }

    @PostMapping("/{chatId}/sendMessage")
    public @ResponseBody ResponseEntity sendMessage(@RequestParam String messageText,
                                      @PathVariable("chatId") String chatId) {
        ResponseEntity result = chatService.createMessage(chatId, messageText);
        return result;
    }
}
