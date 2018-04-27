package ru.sberbank.socialnetwork.webui.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.socialnetwork.webui.models.Chat;
import ru.sberbank.socialnetwork.webui.models.MessageForView;
import ru.sberbank.socialnetwork.webui.models.UserInfo;
import ru.sberbank.socialnetwork.webui.services.ChatService;
import ru.sberbank.socialnetwork.webui.services.GroupService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class ChatController {

    private static final String SESSION_ATTR_USER = "userId";
    private final ChatService chatService;
    private final GroupService groupService;

    @Autowired
    public ChatController(ChatService chatService, GroupService groupService) {
        this.chatService = chatService;
        this.groupService = groupService;
    }

    @GetMapping("/chat/{chatId}/loadMessages")
    @ResponseBody
    public List<MessageForView> showChats(@PathVariable("chatId") String chatId) {
        List<MessageForView> messages = chatService.loadMessagesOfChat(chatId);
        return messages;
    }

    @PostMapping("/chat/{chatId}/sendMessage")
    @ResponseBody
    public ResponseEntity sendMessage(@RequestParam String messageText,
                                      @PathVariable("chatId") String chatId,
                                      @SessionAttribute(SESSION_ATTR_USER) String userId) {
        ResponseEntity result = chatService.createMessage(userId, chatId, messageText);
        return result;
    }

    @GetMapping("/groups/{groupId}/newChat")
    public String newChat(Model model, @PathVariable("groupId") String groupId,
                          @SessionAttribute(SESSION_ATTR_USER) String userId) {
        Chat chat = new Chat();
        model.addAttribute("chat", chat);
        Set<UserInfo> userList = groupService.getUsersOfGroup(groupId);
        userList.removeIf(u -> u.getUuid().equals(userId));
        model.addAttribute("userList", userList);
        return "newChat";
    }

    @PostMapping("/groups/{groupId}/newChat")
    public String createChat(Model model, @ModelAttribute("chat") Chat chat,
                             @PathVariable("groupId") String groupId,
                             @SessionAttribute(SESSION_ATTR_USER) String userId) {
        chatService.createChat(chat, userId, groupId);
        return "redirect:/groups/" + groupId;
    }

    @DeleteMapping("/chat/{chatId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void deleteChat(@PathVariable("chatId") String chatId) {
        chatService.deleteChat(chatId);
    }
}
