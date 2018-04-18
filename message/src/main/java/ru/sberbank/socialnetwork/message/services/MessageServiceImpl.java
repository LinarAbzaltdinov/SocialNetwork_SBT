package ru.sberbank.socialnetwork.message.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sberbank.socialnetwork.message.dao.MessageRepository;
import ru.sberbank.socialnetwork.message.dto.MessageDTO;
import ru.sberbank.socialnetwork.message.entities.Message;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public MessageDTO createMessage(String userId, String chatId, String messageContent) {
        Message newMessage = new Message(messageContent, userId, chatId);
        Message persistedMessage = messageRepository.save(newMessage);
        return modelMapper.map(persistedMessage, MessageDTO.class);
    }

    @Override
    public MessageDTO getMessage(String id) {
        Message foundMessage = messageRepository.findOne(id);
        return modelMapper.map(foundMessage, MessageDTO.class);
    }

    @Override
    public List<MessageDTO> getMessagesOfUser(String userId) {
        List<Message> userMessages = messageRepository.findByUserId(userId);
        return userMessages.stream()
                .map(messageEntity -> modelMapper.map(messageEntity, MessageDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<MessageDTO> getMessagesOfChat(String chatId) {
        List<Message> chatMessages =
                messageRepository.findByChatIdOrderByCreatedDateDesc(chatId);
        return chatMessages.stream()
                .map(messageEntity -> modelMapper.map(messageEntity, MessageDTO.class))
                .collect(Collectors.toList());
    }
}
