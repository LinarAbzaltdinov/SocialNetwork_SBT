package ru.sberbank.socialnetwork.message.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.sberbank.socialnetwork.message.dto.MessageDTO;
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

    private MockMvc mockMvc;
    private ObjectMapper mapper;

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
        mapper = new ObjectMapper();
    }

    MessageDTO getRandomMessageDTO() {
        String messageContent = "Hi";
        String chatId = "2";
        String userId = "u1";
        String createdDate = LocalDateTime.now().toString();
        MessageDTO messageDTO = new MessageDTO(messageContent, userId, chatId, createdDate);
        return messageDTO;
    }

    @Test
    public void showMessage_IfMessageNotFound_ShouldReturnHttpStatusCode404() throws Exception {
        String messageId = Long.toString(new Random().nextLong());
        when(messageService.getMessage(messageId)).thenThrow(new ResourceNotFoundException());

        mockMvc.perform(get("/messages/{id}", messageId))
                .andExpect(status().isNotFound());

        verify(messageService, times(1)).getMessage(messageId);
        verifyNoMoreInteractions(messageService);
    }

    @Test
    public void showMessage_IfMessageFound_ShouldReturnFoundMessage() throws Exception {
        MessageDTO messageDTO = getRandomMessageDTO();

        String messageId = Long.toString(new Random().nextLong());

        when(messageService.getMessage(messageId)).thenReturn(messageDTO);

        mockMvc.perform(get("/messages/{id}", messageId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.userId", is(messageDTO.getUserId())))
                .andExpect(jsonPath("$.content", is(messageDTO.getContent())))
                .andExpect(jsonPath("$.chatId", is(messageDTO.getChatId())))
                .andExpect(jsonPath("$.createdDate", is(messageDTO.getCreatedDate())));

        verify(messageService, times(1)).getMessage(messageId);
        verifyNoMoreInteractions(messageService);
    }

    @Test
    public void createMessage_ShouldReturnSuccess() throws Exception {
        MessageDTO messageDTO = getRandomMessageDTO();

        doNothing().when(messageService).createMessage(messageDTO);

        mockMvc.perform(
                post("/messages/new")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsBytes(messageDTO)))
                .andExpect(status().is2xxSuccessful());

        verify(messageService, times(1)).createMessage(messageDTO);
        verifyNoMoreInteractions(messageService);
    }

    @Test
    public void removeMessage_ShouldReturnHttpStatusCode200() throws Exception {
        String messageId = Long.toString(new Random().nextLong());

        doNothing().when(messageService).removeMessage(messageId);

        mockMvc.perform(delete("/messages/{id}", messageId))
                .andExpect(status().isOk());

        verify(messageService, times(1)).removeMessage(messageId);
        verifyNoMoreInteractions(messageService);
    }

    @Test
    public void showMessagesOfChat_IfChatExists_ShouldReturnListOfMessages() throws Exception {
        String chatId = Long.toString(new Random().nextLong());
        int messagesAmount = new Random().nextInt(90) + 10;
        List<MessageDTO> messagesOfChat = new ArrayList<>(messagesAmount);
        for (Integer i = 0; i < messagesAmount; ++i) {
            messagesOfChat.add(new MessageDTO(
                    "Message" + i,
                    i.toString(),
                    chatId,
                    LocalDateTime.now().toString()));
        }

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
        String userId = Long.toString(new Random().nextLong());
        int messagesAmount = new Random().nextInt(90) + 10;
        List<MessageDTO> messagesOfUser = new ArrayList<>(messagesAmount);
        for (Integer i = 0; i < messagesAmount; ++i) {
            messagesOfUser.add(new MessageDTO(
                    "Message" + i,
                    userId,
                    i.toString(),
                    LocalDateTime.now().toString()));
        }

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
        String chatId = Long.toString(new Random().nextLong());

        doNothing().when(messageService).removeMessagesOfChat(chatId);

        mockMvc.perform(delete("/messages/of/chat/{id}", chatId))
                .andExpect(status().isOk());

        verify(messageService, times(1)).removeMessagesOfChat(chatId);
        verifyNoMoreInteractions(messageService);
    }

    @Test
    public void removeMessagesOfUser_ShouldReturnHttpStatusCode200() throws Exception {
        String userId = Long.toString(new Random().nextLong());

        doNothing().when(messageService).removeMessagesOfUser(userId);

        mockMvc.perform(delete("/messages/of/user/{id}", userId))
                .andExpect(status().isOk());

        verify(messageService, times(1)).removeMessagesOfUser(userId);
        verifyNoMoreInteractions(messageService);
    }
}
