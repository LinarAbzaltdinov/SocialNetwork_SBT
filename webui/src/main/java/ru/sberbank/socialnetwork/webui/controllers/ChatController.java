package ru.sberbank.socialnetwork.webui.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.socialnetwork.webui.models.MessageForView;
import ru.sberbank.socialnetwork.webui.services.ChatService;

import java.util.List;

@Controller
@RequestMapping("/chat")
public class ChatController {

    private static final String SESSION_ATTR_USER = "userId";
    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/{chatId}/loadMessages")
    @ResponseBody
    public List<MessageForView> showChats(@PathVariable("chatId") String chatId) {
        List<MessageForView> messages = chatService.loadMessagesOfChat(chatId);
        return messages;
    }

    @PostMapping("/{chatId}/sendMessage")
    public @ResponseBody ResponseEntity sendMessage(@RequestParam String messageText,
                                                    @PathVariable("chatId") String chatId,
                                                    @SessionAttribute(SESSION_ATTR_USER) String userId) {
        ResponseEntity result = chatService.createMessage(userId, chatId, messageText);
        return result;
    }
}
