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
import java.util.List;
import java.util.stream.Collectors;

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

    public Group createGroup(String groupName, String description, boolean groupIsOpened, String creatorId) {

        Group group = new Group(groupName, description, groupIsOpened, creatorId);
        Group persistedGroup = groupRepository.save(group);

        Chat chat = new Chat(creatorId, MAIN_CHAT_NAME, true, group);
        chatRepository.save(chat);

        GroupUser user = new GroupUser(creatorId, UserAccessMode.ADMINISTRATOR, group);
        group.getUsers().add(user);
        group.getChats().add(chat);
        groupUserRepository.save(user);

        return persistedGroup;
    }

    public List<Group> getUserGroups(String uuid) {
        return groupRepository.findByUsersUuid(uuid);
    }

    public Group getGroupById(Long groupId) {
        return groupRepository.findFirstById(groupId);
    }

    public Collection<Group> getGroupsByPrefix(String prefix) {
        return groupRepository.findByGroupNameStartingWith(prefix);
    }

    public Collection<GroupUser> getGroupUsers(Long groupId) {
        Group group = groupRepository.findFirstById(groupId);
        return group.getUsers();
    }

    public Collection<Group> getUserGroupInvites(String uuid) {
        return groupRepository.findByInvitedUserUuids(uuid);
    }

    public Group inviteUser(Long groupId, String uuid) {
        Group group = groupRepository.findFirstById(groupId);
        GroupUser user = groupUserRepository.findByGroupAndUuid(group, uuid);
        if (user != null) {
            return group;
        }
        group.getInvitedUserUuids().add(uuid);
        return groupRepository.save(group);
    }

    public Group acceptInvite(Long groupId, String uuid) {
        Group group = groupRepository.findFirstById(groupId);

        if (!group.getInvitedUserUuids().remove(uuid)) {
            return group;
        }
        return addUser(groupId, uuid, UserAccessMode.ORDINARY_USER);
    }

    public Group denyInvite(Long groupId, String uuid) {
        Group group = groupRepository.findFirstById(groupId);
        if (!group.getInvitedUserUuids().remove(uuid)) {
            return group;
        }
        return groupRepository.save(group);
    }

    public Group addUser(Long groupId, String uuid, UserAccessMode mode) {
        Group group = groupRepository.findFirstById(groupId);
        GroupUser user = new GroupUser(uuid, mode, group);

        for (Chat chat : group.getChats()) {
            if (chat.isOpened()) {
                chat.getUserUuids().add(uuid);
                chatRepository.save(chat);
            }
        }

        group.getUsers().add(user);
        groupUserRepository.save(user);
        return groupRepository.save(group);
    }

    public Group removeUser(Long groupId, String uuid) {
        Group group = groupRepository.findFirstById(groupId);
        group.getUsers().removeIf(u -> u.getUuid().equals(uuid));

        GroupUser user = groupUserRepository.findByGroupAndUuid(group, uuid);
        groupUserRepository.delete(user);

        return groupRepository.save(group);
    }

    public Group changeGroupParameters(Long groupId, String groupName, String description, boolean isOpened) {
        Group group = groupRepository.findFirstById(groupId);
        group.setGroupName(groupName);
        group.setDescription(description);
        group.setOpened(isOpened);
        return groupRepository.save(group);
    }

    public List<Group> getAllOpenedGroups() {
        return groupRepository.findAllByIsOpenedTrue();
    }

    public void removeGroup(Long groupId) {
        Group group = groupRepository.findFirstById(groupId);

        Collection<Chat> chats = group.getChats();
        for (Chat chat : chats) {
            chatRepository.delete(chat);
        }

        Collection<GroupUser> users = group.getUsers();
        for (GroupUser user : users) {
            groupUserRepository.delete(user);
        }
        groupRepository.delete(group);
    }


    public List<Group> getGroupByAdminId(String uuid) {
        return groupUserRepository.findByAccessModeAndUuid(UserAccessMode.ADMINISTRATOR, uuid)
                .stream()
                .map(GroupUser::getGroup)
                .collect(Collectors.toList());
    }
}

