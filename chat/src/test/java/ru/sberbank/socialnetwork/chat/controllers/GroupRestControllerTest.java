package ru.sberbank.socialnetwork.chat.controllers;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.sberbank.socialnetwork.chat.dto.GroupDto;
import ru.sberbank.socialnetwork.chat.entities.Group;

import java.util.Random;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(GroupRestController.class)
public class GroupRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private GroupRestController groupRestController;

    private Group getGroupExample() {
        Group group = new Group("Grouppp",
                "Interesting group",
                true,
                "uuid");
        group.setId(new Random().nextLong());
        return group;
    }

    @Test
    public void getGroupById_ShouldReturnFoundGroupDto() throws Exception {

        Group group = getGroupExample();
        GroupDto groupDto = new GroupDto(group);

        given(groupRestController.getGroupById(group.getId())).willReturn(groupDto);

        mockMvc.perform(get("/group/{groupId}", group.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(groupDto.getId())))
                .andExpect(jsonPath("$.groupName", is(groupDto.getGroupName())))
                .andExpect(jsonPath("$.description", is(groupDto.getDescription())))
                .andExpect(jsonPath("$.opened", is(groupDto.isOpened())))
                .andExpect(jsonPath("$.numberOfUsers", is(groupDto.getNumberOfUsers())));

    }
}
