package ru.sberbank.socialnetwork.webui.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.socialnetwork.webui.models.Chat;
import ru.sberbank.socialnetwork.webui.models.Group;
import ru.sberbank.socialnetwork.webui.models.Message;
import ru.sberbank.socialnetwork.webui.models.UserInfo;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@FeignClient("chat-service")
public interface ChatServiceClient {
    @PostMapping("/group/create")
    Group createChat(@RequestParam String groupName,
                     @RequestParam String description,
                     @RequestParam boolean isOpened,
                     @RequestParam String creatorId);

    @GetMapping("/group/user/{uuid}")
    Collection<Group> getUserGroups(@PathVariable String uuid);

    @GetMapping("/group/{groupId}")
    Group getGroupById(@PathVariable Long groupId);

    @GetMapping("/group/prefix/{prefix}")
    Collection<Group> getGroupsByPrefix(@PathVariable String prefix);

    @GetMapping("/group/{groupId}/users")
    Collection<UserInfo> getGroupUsers(@PathVariable Long groupId);

    @GetMapping("/group/user/{uuid}/invites")
    Collection<Group> getUserGroupInvites(@PathVariable String uuid);

    @PostMapping("/group/{groupId}/user/{uuid}/invite")
    Group inviteUser(@PathVariable Long groupId, @PathVariable String uuid);

    @PostMapping("/group/{groupId}/user/{uuid}/invite/accept")
    Group acceptInvite(@PathVariable Long groupId, @PathVariable String uuid);

    @PostMapping("/group/{groupId}/user/{uuid}/invite/deny")
    Group denyInvite(@PathVariable Long groupId, @PathVariable String uuid);

    @PostMapping("/group/{groupId}/user/{uuid}/add")
    Group addUser(@PathVariable Long groupId,
                  @PathVariable String uuid,
                  @RequestParam Integer mode);

    @PostMapping("/group/{groupId}/user/{uuid}/remove")
    Group removeUserFromGroup(@PathVariable Long groupId, @PathVariable String uuid);

    @PostMapping("/group/{groupId}/change")
    Group changeGroupParameters(@PathVariable Long groupId,
                                @RequestParam String groupName,
                                @RequestParam String description,
                                @RequestParam boolean isOpened);

    @PostMapping("/group/{groupId}/chat/create")
    Long createChat(@RequestParam String creatorId,
                    @RequestParam String chatName,
                    @PathVariable Long groupId);

    @GetMapping("/group/{groupId}/chat")
    Collection<Chat> getGroupChats(@PathVariable Long groupId);

    @GetMapping("/group/{groupId}/chat/user/{uuid}")
    Collection<Chat> getUserChats(@PathVariable Long groupId, @PathVariable String uuid);

    @GetMapping("/chat/{chatId}")
    Chat getChat(@PathVariable Long chatId);


    @PostMapping("/chat/{chatId}/rename")
    Chat setChatName(@PathVariable Long chatId, @RequestParam String chatName);

    @PostMapping("/chat/{chatId}/user/{uuid}/add")
    Chat addUser(@PathVariable Long chatId, @PathVariable String uuid);

    @PostMapping("/chat/{chatId}/user/{uuid}/remove")
    Chat removeUserFromChat(@PathVariable Long chatId, @PathVariable String uuid);

    @GetMapping("/chat/{chatId}/user")
    Collection<String> getChatUserUuids(@PathVariable Long chatId);

    @PostMapping("/chat/{chatId}/message/send")
    Message sendMessage(@PathVariable Long chatId,
                        @RequestParam String uuid,
                        @RequestParam String messageContent,
                        @RequestParam LocalDateTime date);

    @GetMapping("/chat/{chatId}/message/get")
    List<Message> getMessages(@PathVariable Long chatId);
}