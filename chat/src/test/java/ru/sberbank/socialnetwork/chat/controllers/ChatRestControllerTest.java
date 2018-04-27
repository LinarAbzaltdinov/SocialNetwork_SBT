package ru.sberbank.socialnetwork.chat.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;


import org.springframework.test.web.servlet.MockMvc;
import ru.sberbank.socialnetwork.chat.dto.ChatDto;
import ru.sberbank.socialnetwork.chat.entities.Chat;
import ru.sberbank.socialnetwork.chat.entities.Group;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ChatRestController.class)
public class ChatRestControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private ChatRestController chatRestController;

    private Chat getChatExample(Group group) {
        return new Chat("userId",
                            "MyChat",
                            true, group);
    }

    private Group getGroupExample() {
        Group group = new Group("Grouppp",
                "Interesting group",
                true,
                "uuid");
        group.setId(new Random().nextLong());
        return group;
    }

    @Test
    public void createChat_ShouldReturnChatId() throws Exception {
        Group group = getGroupExample();
        Chat chat = getChatExample(group);

        Long groupId = group.getId();
        Long chatId = 1234L;
        given(chatRestController.createChat(chat.getCreatorId(),
                chat.getChatName(),
                chat.isOpened(),
                groupId))
                .willReturn(chatId);

        mvc.perform(post("/group/{groupId}/chat/create",  groupId)
                .param("creatorId", chat.getCreatorId())
                .param("chatName", chat.getChatName())
                .param("isOpened","true"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", is(chatId.intValue())));
    }

    @Test
    public void getGroupChats_ShouldReturnChatList() throws Exception {

        Group group = getGroupExample();
        Long groupId = group.getId();
        Chat chat1 = new Chat("userId", "MyChat",true, group);
        Chat chat2 = new Chat("userId", "MyChat",true, group);
        Chat chat3 = new Chat("userId", "MyChat",true, group);
        ChatDto chatDto1 = new ChatDto(chat1);
        ChatDto chatDto2 = new ChatDto(chat2);
        ChatDto chatDto3 = new ChatDto(chat3);

        Long chat1Id = 1234L;
        Long chat2Id = 12334L;
        Long chat3Id = 12234L;

        chatDto1.setId(chat1Id);
        chatDto2.setId(chat2Id);
        chatDto3.setId(chat3Id);

        List<ChatDto> chats = new ArrayList<>();
        chats.add(chatDto1);
        chats.add(chatDto2);
        chats.add(chatDto3);

        given(chatRestController.getGroupChats(groupId))
                .willReturn(chats);

        mvc.perform(get("/group/{groupId}/chat",  groupId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].id", is(chatDto1.getId().intValue())))
                .andExpect(jsonPath("$[0].chatName", is(chatDto1.getChatName())))
                .andExpect(jsonPath("$[0].creatorId", is(chatDto1.getCreatorId())))
                .andExpect(jsonPath("$[0].groupId", is(chatDto1.getGroupId())))
                .andExpect(jsonPath("$[0].opened", is(chatDto1.isOpened())))
                .andExpect(jsonPath("$[1].id", is(chatDto2.getId().intValue())))
                .andExpect(jsonPath("$[1].chatName", is(chatDto2.getChatName())))
                .andExpect(jsonPath("$[1].creatorId", is(chatDto2.getCreatorId())))
                .andExpect(jsonPath("$[1].groupId", is(chatDto2.getGroupId())))
                .andExpect(jsonPath("$[1].opened", is(chatDto2.isOpened())))
                .andExpect(jsonPath("$[2].id", is(chatDto3.getId().intValue())))
                .andExpect(jsonPath("$[2].chatName", is(chatDto3.getChatName())))
                .andExpect(jsonPath("$[2].creatorId", is(chatDto3.getCreatorId())))
                .andExpect(jsonPath("$[2].groupId", is(chatDto3.getGroupId())))
                .andExpect(jsonPath("$[2].opened", is(chatDto3.isOpened())));
    }

    @Test
    public void getChat_ShouldReturnChat() throws Exception {
        Group group = getGroupExample();
        Chat chat = getChatExample(group);
        ChatDto chatDto = new ChatDto(chat);
        Long chatId = 1234L;
        chatDto.setId(chatId);
        Long groupId = group.getId();

        given(chatRestController.getChat(chatId))
                .willReturn(chatDto);

        mvc.perform(get("/chat/{chatId}",  chatId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(chatId.intValue())))
                .andExpect(jsonPath("$.chatName", is(chatDto.getChatName())))
                .andExpect(jsonPath("$.creatorId", is(chatDto.getCreatorId())))
                .andExpect(jsonPath("$.groupId", is(chatDto.getGroupId())))
                .andExpect(jsonPath("$.opened", is(chatDto.isOpened())));
    }
}
