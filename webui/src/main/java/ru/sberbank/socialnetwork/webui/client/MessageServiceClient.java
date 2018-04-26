package ru.sberbank.socialnetwork.webui.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.socialnetwork.webui.models.Chat;
import ru.sberbank.socialnetwork.webui.models.Group;
import ru.sberbank.socialnetwork.webui.models.Message;

import java.time.LocalDateTime;
import java.util.List;

@FeignClient("message-service")
@RequestMapping("/messages")
public interface MessageServiceClient {
    @GetMapping("/{id}")
    Message showMessage(@PathVariable("id") String id);

    @PostMapping("/new")
    ResponseEntity createMessage(@RequestBody Message message);

    @GetMapping("/of/chat/{chatId}")
    public List<Message> showMessagesOfChat(@PathVariable("chatId") String chatId);
}