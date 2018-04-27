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
import ru.sberbank.socialnetwork.chat.dto.UserDto;
import ru.sberbank.socialnetwork.chat.entities.Group;
import ru.sberbank.socialnetwork.chat.entities.UserAccessMode;

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
@WebMvcTest(GroupRestController.class)
public class GroupRestControllerTest {

    @Autowired
    MockMvc mvc;

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
    public void createGroup_ShouldReturnCreatedGroup() throws Exception {

        Group group = getGroupExample();
        GroupDto groupDto = new GroupDto(group);
        groupDto.setNumberOfUsers(1);

        given(groupRestController.createGroup(group.getGroupName(),
                group.getDescription(),
                group.isOpened(),
                "qwerty"))
                .willReturn(groupDto);

        mvc.perform(post("/group/create")
                .param("groupName",group.getGroupName())
                .param("description",group.getDescription())
                .param("isOpened","true")
                .param("creatorId","qwerty"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(groupDto.getId())))
                .andExpect(jsonPath("$.groupName", is(groupDto.getGroupName())))
                .andExpect(jsonPath("$.description", is(groupDto.getDescription())))
                .andExpect(jsonPath("$.opened", is(groupDto.isOpened())))
                .andExpect(jsonPath("$.numberOfUsers", is(groupDto.getNumberOfUsers())));
    }

    @Test
    public void getUserGroups_ShouldReturnGroupOfSingleUser() throws Exception {

        String uuid = "userid";
        Group group = getGroupExample();
        GroupDto groupDto = new GroupDto(group);
        groupDto.setNumberOfUsers(6);


        List<GroupDto> groups = new ArrayList<>();

        groups.add(groupDto);
        given(groupRestController.getUserGroups(uuid))
                .willReturn(groups);

        mvc.perform(get("/group/user/{uuid}", uuid))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].id", is(groupDto.getId())))
                .andExpect(jsonPath("$[0].groupName", is(groupDto.getGroupName())))
                .andExpect(jsonPath("$[0].description", is(groupDto.getDescription())))
                .andExpect(jsonPath("$[0].opened", is(groupDto.isOpened())))
                .andExpect(jsonPath("$[0].numberOfUsers", is(groupDto.getNumberOfUsers())));

    }

    @Test
    public void getGroup_IfGroupFound_ShouldReturnFoundGroup() throws Exception {
        Group group = getGroupExample();
        GroupDto groupDto = new GroupDto(group);
        groupDto.setNumberOfUsers(10);
        given(groupRestController.getGroupById(group.getId()))
                .willReturn(groupDto);

        mvc.perform(get("/group/{id}", groupDto.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(groupDto.getId())))
                .andExpect(jsonPath("$.groupName", is(groupDto.getGroupName())))
                .andExpect(jsonPath("$.description", is(groupDto.getDescription())))
                .andExpect(jsonPath("$.opened", is(groupDto.isOpened())))
                .andExpect(jsonPath("$.numberOfUsers", is(groupDto.getNumberOfUsers())));

    }


    @Test
    public void getGroupUsers_ShouldReturnUsersInThisGroup() throws Exception {

        UserDto user1 = new UserDto("userid1", UserAccessMode.ADMINISTRATOR);
        UserDto user2 = new UserDto("userid2", UserAccessMode.ORDINARY_USER);
        UserDto user3 = new UserDto("userid3", UserAccessMode.ORDINARY_USER);
        Long groupId = 123456L;

        List<UserDto> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);

        given(groupRestController.getGroupUsers(groupId))
                .willReturn(users);

        mvc.perform(get("/group/{groupId}/users", groupId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].uuid", is(user1.getUuid())))
                .andExpect(jsonPath("$[0].accessMode", is(user1.getAccessMode().toString())))
                .andExpect(jsonPath("$[1].uuid", is(user2.getUuid())))
                .andExpect(jsonPath("$[1].accessMode", is(user2.getAccessMode().toString())))
                .andExpect(jsonPath("$[2].uuid", is(user3.getUuid())))
                .andExpect(jsonPath("$[2].accessMode", is(user3.getAccessMode().toString())));
    }
}
