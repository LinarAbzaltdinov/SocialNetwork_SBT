package ru.sberbank.socialnetwork.message.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sberbank.socialnetwork.message.dao.MessageRepository;
import ru.sberbank.socialnetwork.message.entities.Message;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public Message createMessage(String userId, String messageContent) {
        Message newMessage = new Message(messageContent, userId);
        Message persistedMessage = messageRepository.save(newMessage);
        return persistedMessage;
    }

    @Override
    public Message getMessage(String id) {
        Message foundMessage = messageRepository.findOne(id);
        return foundMessage;
    }
}
