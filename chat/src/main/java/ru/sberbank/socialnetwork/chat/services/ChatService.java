package ru.sberbank.socialnetwork.chat.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sberbank.socialnetwork.chat.client.MessageServiceClient;
import ru.sberbank.socialnetwork.chat.dao.ChatRepository;
import ru.sberbank.socialnetwork.chat.entities.Chat;

import java.io.IOException;
import java.util.Collection;

@Service
public class ChatService {
    private final ObjectMapper mapper = new ObjectMapper();
    private final ChatRepository chatRepository;
    private final MessageServiceClient messageServiceClient;

    @Autowired
    public ChatService(ChatRepository chatRepository,
                       MessageServiceClient messageServiceClient) {
        this.chatRepository = chatRepository;
        this.messageServiceClient = messageServiceClient;
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
        Chat chat = chatRepository.findById(id);
        return chat;
    }

    public Chat sendMessage(Long id, String messageId) {
        Chat chat = chatRepository.findById(id);
        chat.getMessages().add(messageId);
        return chatRepository.save(chat);
    }
}

