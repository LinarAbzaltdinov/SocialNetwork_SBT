package ru.sberbank.socialnetwork.chat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sberbank.socialnetwork.chat.dao.ChatRepository;
import ru.sberbank.socialnetwork.chat.entities.Chat;

import java.util.Collection;

@Service
public class ChatService {
    private final ChatRepository chatRepository;

    @Autowired
    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public Chat createChat(String creatorId, String chatName) {
        Chat chat = new Chat(creatorId, chatName);
        return chatRepository.save(chat);
    }

    public Collection<Chat> getUserChats(String uuid) {
        return chatRepository.findByUsers(uuid);
    }

    public Collection<String> getChatUsers(Long id) {
        Chat chat = chatRepository.findById(id);
        return chat.getUsers();
    }

    public Chat addUser(Long chatId, String uuid) {
        Chat chat = chatRepository.findById(chatId);
        chat.getUsers().add(uuid);
        return chatRepository.save(chat);
    }

    public Chat removeUser(Long chatId, String uuid) {
        Chat chat = chatRepository.findById(chatId);
        chat.getUsers().remove(uuid);
        return chatRepository.save(chat);
    }

    public Chat getChat(Long id) {
        return chatRepository.findById(id);
    }

    public Chat sendMessage(Long id, String messageId) {
        Chat chat = chatRepository.findById(id);
        chat.getMessages().add(messageId);
        return chatRepository.save(chat);
    }
}

