package ru.sberbank.socialnetwork.message.services;

import org.springframework.stereotype.Service;
import ru.sberbank.socialnetwork.message.entities.Message;

public interface MessageService {

    Message createMessage(String userId, String messageContent);
    Message getMessage(String id);

}
