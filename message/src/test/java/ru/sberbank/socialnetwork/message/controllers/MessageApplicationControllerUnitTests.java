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
import ru.sberbank.socialnetwork.message.services.MessageServiceImpl;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageApplicationControllerUnitTests {

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
                .build();
    }

    @Test
    public void getMessage_MessageNotFound_ShouldReturnHttpStatusCode404() throws Exception {
        String messageId = "1";
        when(messageService.getMessage(messageId)).thenReturn(null);

        mockMvc.perform(get("/messages/{id}", messageId))
                .andExpect(status().isNotFound());

        verify(messageService, times(1)).getMessage(messageId);
        verifyNoMoreInteractions(messageService);
    }

    @Test
    public void getMessage_MessageFound_ShouldReturnFoundMessage() throws Exception {
        String messageId = "1";
        String messageContent = "Hi";
        String chatId = "2";
        String userId = "u1";
        String createdDate = "2018-01-01 12:12";

        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setChatId(chatId);
        messageDTO.setContent(messageContent);
        messageDTO.setUserId(userId);
        messageDTO.setId(messageId);
        messageDTO.setCreatedDate(createdDate);

        when(messageService.getMessage(messageId)).thenReturn(messageDTO);

        mockMvc.perform(get("/messages/{id}", messageId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(messageId)))
                .andExpect(jsonPath("$.userId", is(userId)))
                .andExpect(jsonPath("$.content", is(messageContent)))
                .andExpect(jsonPath("$.chatId", is(chatId)))
                .andExpect(jsonPath("$.createdDate", is(createdDate)));

        verify(messageService, times(1)).getMessage(messageId);
        verifyNoMoreInteractions(messageService);
    }
}
