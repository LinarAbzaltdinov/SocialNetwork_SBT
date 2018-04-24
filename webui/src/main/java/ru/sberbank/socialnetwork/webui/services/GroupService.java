package ru.sberbank.socialnetwork.webui.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sberbank.socialnetwork.webui.client.ChatServiceClient;
import ru.sberbank.socialnetwork.webui.models.Group;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class GroupService {

    private final ChatServiceClient chatServiceClient;

    @Autowired
    public GroupService(ChatServiceClient chatServiceClient) {
        this.chatServiceClient = chatServiceClient;
    }

    public String getGroups(String userId) {
        String userGroups = chatServiceClient.getUserGroups(userId);
        return userGroups;
    }

    public static Group createGroup(String authToken, Group group) {
        return new Group();
    }

    public List<Group> getAllGroups(String id) {
        return new ArrayList<>();
    }
}
