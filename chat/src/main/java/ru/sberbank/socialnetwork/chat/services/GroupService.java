package ru.sberbank.socialnetwork.chat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sberbank.socialnetwork.chat.dao.ChatRepository;
import ru.sberbank.socialnetwork.chat.dao.GroupRepository;
import ru.sberbank.socialnetwork.chat.dao.GroupUserRepository;
import ru.sberbank.socialnetwork.chat.entities.Chat;
import ru.sberbank.socialnetwork.chat.entities.Group;
import ru.sberbank.socialnetwork.chat.entities.GroupUser;
import ru.sberbank.socialnetwork.chat.entities.UserAccessMode;

import java.util.Collection;

@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final GroupUserRepository groupUserRepository;
    private final ChatRepository chatRepository;

    private static final String MAIN_CHAT_NAME = "Main chat";


    @Autowired
    public GroupService(GroupRepository groupRepository,
                        ChatRepository chatRepository,
                        GroupUserRepository groupUserRepository) {
        this.groupRepository = groupRepository;
        this.groupUserRepository = groupUserRepository;
        this.chatRepository = chatRepository;
    }

    public Group createGroup(String groupName, String description, boolean isOpened, String creatorId) {

        Group group = new Group(groupName, description, isOpened, creatorId);
        Chat chat = new Chat(creatorId,  MAIN_CHAT_NAME, group);
        GroupUser user = new GroupUser(creatorId, UserAccessMode.ADMINISTRATOR, group);

        group.getUsers().add(user);
        group.getChats().add(chat);

        groupUserRepository.save(user);
        chatRepository.save(chat);

        return groupRepository.save(group);
    }

    public Collection<Group> getUserGroups(String uuid) {
        return groupUserRepository.findUserGroups(uuid);
    }

    public Group getGroupById(Long groupId) {
        return groupRepository.findById(groupId);
    }

    public Collection<Group> getGroupsByPrefix(String prefix) {
        return groupRepository.findByGroupNameStartingWith(prefix);
    }

    public Collection<GroupUser> getGroupUsers(Long groupId) {
        Group group = groupRepository.findById(groupId);
        return group.getUsers();
    }

    public Collection<Group> getUserGroupInvites(String uuid) {
        return groupRepository.findByInvitedUserUuids(uuid);
    }

    public Group inviteUser(Long groupId, String uuid) {
        Group group = groupRepository.findById(groupId);
        GroupUser user = groupUserRepository.findByGroupAndUuid(group, uuid);
        if (user != null) {
            return group;
        }
        group.getInvitedUserUuids().add(uuid);
        return groupRepository.save(group);
    }

    public Group acceptInvite(Long groupId, String uuid) {
        Group group = groupRepository.findById(groupId);

        if(!group.getInvitedUserUuids().remove(uuid)) {
            return group;
        }
        return addUser(groupId, uuid, UserAccessMode.ORDINARY_USER);
    }

    public Group denyInvite(Long groupId, String uuid) {
        Group group = groupRepository.findById(groupId);
        if(!group.getInvitedUserUuids().remove(uuid)) {
            return group;
        }
        return groupRepository.save(group);
    }

    public Group addUser(Long groupId, String uuid, UserAccessMode mode) {
        Group group = groupRepository.findById(groupId);
        GroupUser user = new GroupUser(uuid, mode, group);
        group.getUsers().add(user);
        return groupRepository.save(group);
    }

    public Group removeUser(Long groupId, String uuid) {
        Group group = groupRepository.findById(groupId);
        group.getUsers().removeIf(user -> user.getUuid().equals(uuid));
        return groupRepository.save(group);
    }

    public Group changeGroupParameters(Long groupId, String groupName, String description, boolean isOpened) {
        Group group = groupRepository.findById(groupId);
        group.setGroupName(groupName);
        group.setDescription(description);
        group.setOpened(isOpened);
        return groupRepository.save(group);
    }
}

