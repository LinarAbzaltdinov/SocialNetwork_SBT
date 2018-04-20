package ru.sberbank.socialnetwork.message.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.sberbank.socialnetwork.message.AppConfig;
import ru.sberbank.socialnetwork.message.dto.MessageDTO;
import ru.sberbank.socialnetwork.message.entities.Message;
import ru.sberbank.socialnetwork.message.exceptions.ResourceNotFoundException;
import ru.sberbank.socialnetwork.message.services.MessageServiceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageControllerUnitTests {

    MockMvc mockMvc;

    @Mock
    private MessageServiceImpl messageService;

    @InjectMocks
    private MessageRestController messageRestController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(messageRestController)
                .setControllerAdvice(new ResourceNotFoundExceptionHandler())
                .build();
    }

    MessageDTO getRandomMessageDTO() {
        String messageId = Long.toString(new Random().nextLong());
        String messageContent = "Hi";
        String chatId = "2";
        String userId = "u1";
        String createdDate = LocalDateTime.now().toString();
        MessageDTO messageDTO = new MessageDTO(messageId, messageContent, userId, chatId, createdDate);
        return messageDTO;
    }

    @Test
    public void showMessage_IfMessageNotFound_ShouldReturnHttpStatusCode404() throws Exception {
        String messageId = "1";
        when(messageService.getMessage(messageId)).thenThrow(new ResourceNotFoundException());

        mockMvc.perform(get("/messages/{id}", messageId))
                .andExpect(status().isNotFound());

        verify(messageService, times(1)).getMessage(messageId);
        verifyNoMoreInteractions(messageService);
    }

    @Test
    public void showMessage_IfMessageFound_ShouldReturnFoundMessage() throws Exception {
        MessageDTO messageDTO = getRandomMessageDTO();

        when(messageService.getMessage(messageDTO.getId())).thenReturn(messageDTO);

        mockMvc.perform(get("/messages/{id}", messageDTO.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(messageDTO.getId())))
                .andExpect(jsonPath("$.userId", is(messageDTO.getUserId())))
                .andExpect(jsonPath("$.content", is(messageDTO.getContent())))
                .andExpect(jsonPath("$.chatId", is(messageDTO.getChatId())))
                .andExpect(jsonPath("$.createdDate", is(messageDTO.getCreatedDate())));

        verify(messageService, times(1)).getMessage(messageDTO.getId());
        verifyNoMoreInteractions(messageService);
    }

    @Test
    public void createMessage_ShouldReturnMessageId() throws Exception {
        MessageDTO messageDTO = getRandomMessageDTO();

        when(messageService.createMessage(messageDTO.getUserId(), messageDTO.getChatId(), messageDTO.getContent()))
                .thenReturn(messageDTO);

        mockMvc.perform(
                post("/messages/new")
                        .param("userId", messageDTO.getUserId())
                        .param("chatId", messageDTO.getChatId())
                        .param("messageContent", messageDTO.getContent()))
                .andExpect(status().isOk())
                .andExpect(content().string(messageDTO.getId()));

        verify(messageService, times(1))
                .createMessage(messageDTO.getUserId(), messageDTO.getChatId(), messageDTO.getContent());
        verifyNoMoreInteractions(messageService);
    }

    @Test
    public void removeMessage_ShouldReturnHttpStatusCode200() throws Exception {
        String messageId = "1";

        doNothing().when(messageService).removeMessage(messageId);

        mockMvc.perform(delete("/messages/{id}", messageId))
                .andExpect(status().isOk());

        verify(messageService, times(1)).removeMessage(messageId);
        verifyNoMoreInteractions(messageService);
    }

    @Test
    public void showMessagesOfChat_IfChatExists_ShouldReturnListOfMessages() throws Exception {
        String chatId = "1";
        //String anotherChatId = "2";
        List<MessageDTO> messagesOfChat = new ArrayList<>();
        messagesOfChat.add(
                new MessageDTO("1", "Message1", "1", chatId, LocalDateTime.now().toString()));
        messagesOfChat.add(
                new MessageDTO("2", "Message2", "2", chatId, LocalDateTime.now().toString()));
        //new MessageDTO("2", "Message3", "1", anotherChatId, LocalDateTime.now().toString())

        when(messageService.getMessagesOfChat(chatId)).thenReturn(messagesOfChat);

        mockMvc.perform(get("/messages/of/chat/{chatId}", chatId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(messagesOfChat.size())))
                .andExpect(jsonPath("$[*].chatId", everyItem(is(chatId))));

        verify(messageService, times(1)).getMessagesOfChat(chatId);
        verifyNoMoreInteractions(messageService);
    }

    @Test
    public void showMessagesOfUser_IfChatIsNotExists_ShouldReturnEmptyList() throws Exception {
        String userId = "1";
        List<MessageDTO> messagesOfUser = new ArrayList<>();
        messagesOfUser.add(
                new MessageDTO("1", "Message1", userId, "1", LocalDateTime.now().toString()));
        messagesOfUser.add(
                new MessageDTO("2", "Message2", userId, "2", LocalDateTime.now().toString()));

        when(messageService.getMessagesOfUser(userId)).thenReturn(messagesOfUser);

        mockMvc.perform(get("/messages/of/user/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(messagesOfUser.size())))
                .andExpect(jsonPath("$[*].userId", everyItem(is(userId))));

        verify(messageService, times(1)).getMessagesOfUser(userId);
        verifyNoMoreInteractions(messageService);
    }

    @Test
    public void removeMessagesOfChat_ShouldReturnHttpStatusCode200() throws Exception {
        String chatId = "1";
        List<MessageDTO> messagesOfChat = new ArrayList<>();
        messagesOfChat.add(
                new MessageDTO("1", "Message1", "1", chatId, LocalDateTime.now().toString()));
        messagesOfChat.add(
                new MessageDTO("2", "Message2", "2", chatId, LocalDateTime.now().toString()));

        doNothing().when(messageService).removeMessagesOfChat(chatId);

        mockMvc.perform(delete("/messages/of/chat/{id}", chatId))
                .andExpect(status().isOk());

        verify(messageService, times(1)).removeMessagesOfChat(chatId);
        verifyNoMoreInteractions(messageService);
    }

    @Test
    public void removeMessagesOfUser_ShouldReturnHttpStatusCode200() throws Exception {
        String userId = "1";
        List<MessageDTO> messagesOfUser = new ArrayList<>();
        messagesOfUser.add(
                new MessageDTO("1", "Message1", userId, "1", LocalDateTime.now().toString()));
        messagesOfUser.add(
                new MessageDTO("2", "Message2", userId, "2", LocalDateTime.now().toString()));

        doNothing().when(messageService).removeMessagesOfUser(userId);

        mockMvc.perform(delete("/messages/of/user/{id}", userId))
                .andExpect(status().isOk());

        verify(messageService, times(1)).removeMessagesOfUser(userId);
        verifyNoMoreInteractions(messageService);
    }
}
