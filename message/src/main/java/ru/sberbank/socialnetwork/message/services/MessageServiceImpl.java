package ru.sberbank.socialnetwork.message.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sberbank.socialnetwork.message.dao.MessageRepository;
import ru.sberbank.socialnetwork.message.dto.MessageDTO;
import ru.sberbank.socialnetwork.message.domains.Message;
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
    public void createMessage(MessageDTO messageDTO) {
        Message newMessage = modelMapper.map(messageDTO, Message.class);
        Message persistedMessage = messageRepository.save(newMessage);
    }

    @Override
    public MessageDTO getMessage(String id) {
        Message foundMessage = messageRepository.findOne(id);
        return foundMessage == null
               ? null
               : modelMapper.map(foundMessage, MessageDTO.class);
    }

    @Override
    public void removeMessage(String id) {
        messageRepository.delete(id);
    }

    @Override
    public List<MessageDTO> getMessagesOfUser(String userId) {
        List<Message> userMessages = messageRepository.findByUserId(userId);
        return userMessages.stream()
                .map(messageEntity -> modelMapper.map(messageEntity, MessageDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void removeMessagesOfUser(String userId) {
        messageRepository.deleteAllByUserId(userId);
    }

    @Override
    public List<MessageDTO> getMessagesOfChat(String chatId) {
        List<Message> chatMessages =
                messageRepository.findByChatIdOrderByCreatedDateAsc(chatId);
        return chatMessages.stream()
                .map(messageEntity -> modelMapper.map(messageEntity, MessageDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void removeMessagesOfChat(String chatId) {
        messageRepository.deleteAllByChatId(chatId);
    }
}
