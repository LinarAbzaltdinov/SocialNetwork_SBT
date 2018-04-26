package ru.sberbank.socialnetwork.webui.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sberbank.socialnetwork.webui.client.ChatServiceClient;
import ru.sberbank.socialnetwork.webui.models.Chat;
import ru.sberbank.socialnetwork.webui.models.Group;
import ru.sberbank.socialnetwork.webui.models.UserInfo;

import java.util.ArrayList;
import java.util.Collection;
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

    public List<Group> getAllGroups(String id) {
        return new ArrayList<>();
    }

    public List<Chat> chatOfGroups(String groupId) {
        return chatServiceClient.getGroupChats(groupId);
    }

    public boolean isUserCreatorOfGroup(String userId, String groupId) {
//        Group group = chatServiceClient.
//        if (group == null) {
//            return false;
//        }
//        group.
        return true;
    }
}
