package ru.sberbank.socialnetwork.message.services;

import ru.sberbank.socialnetwork.message.dto.MessageDTO;

import java.util.List;

public interface MessageService {

    void createMessage(MessageDTO message);

    MessageDTO getMessage(String id);

    void removeMessage(String id);

    List<MessageDTO> getMessagesOfUser(String userId);

    void removeMessagesOfUser(String userId);

    List<MessageDTO> getMessagesOfChat(String chatId);

    void removeMessagesOfChat(String chatId);

}
