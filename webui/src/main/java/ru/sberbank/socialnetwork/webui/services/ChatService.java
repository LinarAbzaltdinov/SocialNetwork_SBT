package ru.sberbank.socialnetwork.webui.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.sberbank.socialnetwork.webui.client.ChatServiceClient;
import ru.sberbank.socialnetwork.webui.client.MessageServiceClient;
import ru.sberbank.socialnetwork.webui.converters.MessageToMessageForViewConverter;
import ru.sberbank.socialnetwork.webui.models.Chat;
import ru.sberbank.socialnetwork.webui.models.Message;
import ru.sberbank.socialnetwork.webui.models.MessageForView;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ChatService {

    private final MessageServiceClient messageServiceClient;
    private final MessageToMessageForViewConverter messageConverter;
    private final ChatServiceClient chatServiceClient;

    @Autowired
    public ChatService(MessageServiceClient messageServiceClient,
                       MessageToMessageForViewConverter messageConverter,
                       ChatServiceClient chatServiceClient) {
        this.messageServiceClient = messageServiceClient;
        this.messageConverter = messageConverter;
        this.chatServiceClient = chatServiceClient;
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

    public void createChat(Chat chat, String userId, String groupId) {
        chat.setCreatorId(userId);
        chat.setGroupId(groupId);
        chatServiceClient.createChat(groupId, chat);
        if (!chat.isOpened()) {
            for (String userIdFromChat : chat.getUserList()) {
                chatServiceClient.addUserToGroup(groupId, userIdFromChat, 1);
            }
        }
    }

    public void deleteChat(String chatId) {
        chatServiceClient.removeChat(chatId);
    }
}
