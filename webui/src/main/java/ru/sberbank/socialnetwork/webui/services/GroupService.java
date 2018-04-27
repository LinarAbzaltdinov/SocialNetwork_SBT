package ru.sberbank.socialnetwork.webui.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sberbank.socialnetwork.webui.client.ChatServiceClient;
import ru.sberbank.socialnetwork.webui.models.Chat;
import ru.sberbank.socialnetwork.webui.models.Group;

import java.util.List;

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

    public List<Chat> chatOfGroups(String groupId) {
        return chatServiceClient.getGroupChats(groupId);
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
}
