package ru.sberbank.socialnetwork.webui.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.sberbank.socialnetwork.webui.client.MessageServiceClient;
import ru.sberbank.socialnetwork.webui.converters.MessageToMessageForViewConverter;
import ru.sberbank.socialnetwork.webui.models.Message;
import ru.sberbank.socialnetwork.webui.models.MessageForView;
import ru.sberbank.socialnetwork.webui.models.UserInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ChatService {

    private final MessageServiceClient messageServiceClient;
    private final MessageToMessageForViewConverter messageConverter;

    @Autowired
    public ChatService(MessageServiceClient messageServiceClient, MessageToMessageForViewConverter messageConverter) {
        this.messageServiceClient = messageServiceClient;
        this.messageConverter = messageConverter;
    }

    public ResponseEntity createMessage(String userId, String chatId, String messageText) {
        Message newMessage = new Message(chatId, userId, messageText);
        ResponseEntity responseEntity = messageServiceClient.createMessage(newMessage);
        return responseEntity;
    }

    public List<MessageForView> loadMessagesOfChat(String chatId) {
        List<Message> messages = messageServiceClient.showMessagesOfChat(chatId);
        List<MessageForView> messagesForView = messages.stream()
                .map(messageConverter::convert)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return messagesForView;
    }
}
