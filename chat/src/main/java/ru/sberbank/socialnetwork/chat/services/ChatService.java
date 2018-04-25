package ru.sberbank.socialnetwork.chat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sberbank.socialnetwork.chat.dao.ChatRepository;
import ru.sberbank.socialnetwork.chat.dao.GroupRepository;
import ru.sberbank.socialnetwork.chat.entities.Chat;
import ru.sberbank.socialnetwork.chat.entities.Group;

import java.util.Collection;

@Service
public class ChatService {

    private final ChatRepository chatRepository;
    private final GroupRepository groupRepository;

    @Autowired
    public ChatService(ChatRepository chatRepository, GroupRepository groupRepository) {
        this.chatRepository = chatRepository;
        this.groupRepository = groupRepository;
    }

    public Chat createChat(String creatorId, String chatName, Long groupId) {
        Group group = groupRepository.findFirstById(groupId);
        Chat chat = new Chat(creatorId, chatName, group);
        return chatRepository.save(chat);
    }

    public Chat getChat(Long chatId) {
        return chatRepository.findById(chatId);
    }

    public Chat setChatName(Long chatId, String chatName) {
        Chat chat = chatRepository.findById(chatId);
        chat.setChatName(chatName);
        return chatRepository.save(chat);
    }


    public Collection<Chat> getGroupChats(Long groupId) {
        Group group = groupRepository.findFirstById(groupId);
        return chatRepository.findByGroup(group);
    }

    public Collection<Chat> getUserChats(Long groupId, String uuid) {
        Group group = groupRepository.findFirstById(groupId);
        return chatRepository.findByUserUuidsAndGroup(uuid, group);
    }


    public Chat addUser(Long chatId, String uuid) {
        Chat chat = chatRepository.findById(chatId);
        chat.getUserUuids().add(uuid);
        return chatRepository.save(chat);
    }

    public Chat removeUser(Long chatId, String uuid) {
        Chat chat = chatRepository.findById(chatId);
        chat.getUserUuids().remove(uuid);
        return chatRepository.save(chat);
    }

    public Collection<String> getChatUserUuids(Long chatId) {
        Chat chat = chatRepository.findById(chatId);
        return chat.getUserUuids();
    }
}

