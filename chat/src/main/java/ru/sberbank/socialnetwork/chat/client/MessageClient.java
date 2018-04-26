package ru.sberbank.socialnetwork.chat.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.socialnetwork.chat.dto.MessageDto;

import java.util.Collection;
import java.util.List;

@FeignClient("message-service")
@RequestMapping("/messages")
public interface MessageClient {
    @GetMapping("/group/user/{uuid}")
    Collection<MessageDto> getUserGroups(@PathVariable("uuid") String uuid);


    @GetMapping("/{id}")
    public MessageDto showMessage(@PathVariable("id") String id);

    @PostMapping("/new")
    public String createMessage(@RequestParam String messageContent,
                                @RequestParam String userId,
                                @RequestParam Long chatId);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    void removeMessage(@PathVariable("id") String id);

    @GetMapping("/of/chat/{chatId}")
    List<MessageDto> showMessagesOfChat(@PathVariable("chatId") Long chatId);

    @DeleteMapping("/of/chat/{chatId}")
    @ResponseStatus(HttpStatus.OK)
    void removeMessagesOfChat(@PathVariable("chatId") String chatId);

    @GetMapping("/of/user/{userId}")
    public List<MessageDto> showMessageOfUser(@PathVariable("userId") String userId);

    @DeleteMapping("/of/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    void removeMessagesOfUser(@PathVariable("userId") String userId);
}
