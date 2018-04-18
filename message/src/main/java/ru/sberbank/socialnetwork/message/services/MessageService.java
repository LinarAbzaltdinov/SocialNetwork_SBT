package ru.sberbank.socialnetwork.message.services;

import org.springframework.stereotype.Service;
import ru.sberbank.socialnetwork.message.dto.MessageDTO;
import ru.sberbank.socialnetwork.message.entities.Message;

import java.util.List;

public interface MessageService {

    MessageDTO createMessage(String userId, String chatId, String messageContent);

    MessageDTO getMessage(String id);

    List<MessageDTO> getMessagesOfUser(String userId);

    List<MessageDTO> getMessagesOfChat(String chatId);

}
