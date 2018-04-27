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
    @PostMapping(value = "/group/create")
    @ResponseBody
    Group createGroup(@RequestParam("groupName") String groupName,
                     @RequestParam("description") String description,
                     @RequestParam("isOpened") boolean isOpened,
                     @RequestParam("creatorId") String creatorId);

    @GetMapping(value = "/group/user/{uuid}", consumes = "application/json")
    @ResponseBody
    List<Group> getUserGroups(@PathVariable("uuid") String uuid);

    @GetMapping(value = "/group/opened", consumes = "application/json")
    @ResponseBody
    List<Group> getAllOpenedGroups();

    @GetMapping(value = "/group/{groupId}")
    Group getGroupById(@PathVariable("groupId") String groupId);

    @PostMapping("/group/{groupId}/remove")
    void deleteGroupById(@PathVariable("groupId") String groupId);
//
//    @GetMapping("/group/prefix/{prefix}")
//    Collection<Group> getGroupsByPrefix(@PathVariable String prefix);

    @GetMapping("/group/{groupId}/users")
    List<UserInfo> getGroupUsers(@PathVariable("groupId") String groupId);
//
//    @GetMapping("/group/user/{uuid}/invites")
//    Collection<Group> getUserGroupInvites(@PathVariable String uuid);
//
//    @PostMapping("/group/{groupId}/user/{uuid}/invite")
//    Group inviteUser(@PathVariable Long groupId, @PathVariable String uuid);
//
//    @PostMapping("/group/{groupId}/user/{uuid}/invite/accept")
//    Group acceptInvite(@PathVariable Long groupId, @PathVariable String uuid);
//
//    @PostMapping("/group/{groupId}/user/{uuid}/invite/deny")
//    Group denyInvite(@PathVariable Long groupId, @PathVariable String uuid);

    @PostMapping(value = "/group/{groupId}/user/{uuid}/add")
    Group addUserToGroup(@PathVariable("groupId") String groupId,
                         @PathVariable("uuid") String uuid,
                         @RequestParam("mode") Integer mode);

    @PostMapping(value = "/group/{groupId}/user/{uuid}/remove")
    Group removeUserFromGroup(@PathVariable("groupId") String groupId,
                              @PathVariable("uuid") String uuid);

//    @PostMapping("/group/{groupId}/change")
//    Group changeGroupParameters(@PathVariable Long groupId,
//                                @RequestParam String groupName,
//                                @RequestParam String description,
//                                @RequestParam boolean isOpened);

    @PostMapping(value = "/group/{groupId}/chat/create")
    Long createChat(@PathVariable("groupId") String groupId, @RequestBody Chat chat);

    @GetMapping(value = "/group/{groupId}/chat")
    List<Chat> getGroupChats(@PathVariable("groupId") String groupId);

    @GetMapping("/group/{groupId}/chat/user/{uuid}")
    List<Chat> getUserChats(@PathVariable("groupId") String groupId,
                            @PathVariable("uuid") String uuid);
//
//    @GetMapping("/chat/{chatId}")
//    Chat getChat(@PathVariable Long chatId);
//
//    @PostMapping("/chat/{chatId}/rename")
//    Chat setChatName(@PathVariable Long chatId, @RequestParam String chatName);
//
//    @PostMapping("/chat/{chatId}/user/{uuid}/add")
//    Chat addUserToGroup(@PathVariable Long chatId, @PathVariable String uuid);
//
//    @PostMapping("/chat/{chatId}/user/{uuid}/remove")
//    Chat removeUserFromChat(@PathVariable Long chatId, @PathVariable String uuid);
//
//    @GetMapping("/chat/{chatId}/user")
//    Collection<String> getChatUserUuids(@PathVariable Long chatId);

    @PostMapping(value = "/chat/{chatId}/message/send")
    Message sendMessage(@PathVariable("chatId") Long chatId,
                        @RequestParam("uuid") String uuid,
                        @RequestParam("messageContent") String messageContent,
                        @RequestParam("date") LocalDateTime date);

    @GetMapping(value = "/chat/{chatId}/message/get")
    List<Message> getMessagesOfChat(@PathVariable("chatId") Long chatId);

    @PostMapping("/group/byAdminId")
    List<Group> getGroupByAdminId(@RequestParam("uuid") String userId);

    @PostMapping("/chat/{chatId}/remove")
    void removeChat(@PathVariable("chatId") String chatId);
}