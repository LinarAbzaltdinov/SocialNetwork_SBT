package ru.sberbank.socialnetwork.webui.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sberbank.socialnetwork.webui.client.ChatServiceClient;
import ru.sberbank.socialnetwork.webui.models.Chat;
import ru.sberbank.socialnetwork.webui.models.Group;
import ru.sberbank.socialnetwork.webui.models.UserInfo;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class GroupService {

    private final ChatServiceClient chatServiceClient;
    private final UserInfoService userInfoService;

    @Autowired
    public GroupService(ChatServiceClient chatServiceClient, UserInfoService userInfoService) {
        this.chatServiceClient = chatServiceClient;
        this.userInfoService = userInfoService;
    }

    public List<Group> getGroups(String userId) {
        List<Group> userGroups = chatServiceClient.getUserGroups(userId);
        return userGroups;
    }

    public Group createGroup(String userId, Group newGroup) {
        return chatServiceClient.createGroup(newGroup.getGroupName(),
                newGroup.getDescription(),true, userId);
    }

    public List<Group> getAllOpenedGroups(String id) {
        return chatServiceClient.getAllOpenedGroups();
    }

    public List<Chat> chatOfGroupAndUser(String groupId, String userId) {
        return chatServiceClient.getUserChats(groupId, userId);
    }

    public void addUserToGroup(String userId, String groupId) {
        chatServiceClient.addUserToGroup(groupId, userId, 1);
    }

    public void removeUserFromGroup(String userId, String groupId) {
        chatServiceClient.removeUserFromGroup(groupId, userId);
    }

    public List<Group> getGroupsCreatedByMe(String userId) {
        return chatServiceClient.getGroupByAdminId(userId);
    }

    public void removeGroup(String groupId) {
        chatServiceClient.deleteGroupById(groupId);
    }

    public boolean isUserCreatorOfGroup(String userId, String groupId) {
        return getGroupsCreatedByMe(userId).stream()
                .anyMatch(g -> groupId.equals(g.getId()));
    }

    public List<UserInfo> getUsersOfGroup(String groupId) {
        return chatServiceClient.getGroupUsers(groupId)
                .stream()
                .map(u -> userInfoService.getUser(u.getUuid()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
