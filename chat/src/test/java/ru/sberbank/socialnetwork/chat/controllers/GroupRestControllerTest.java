//package ru.sberbank.socialnetwork.chat.controllers;
//
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.*;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//
//
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import ru.sberbank.socialnetwork.chat.dto.GroupDto;
//import ru.sberbank.socialnetwork.chat.entities.Group;
//import ru.sberbank.socialnetwork.chat.services.GroupService;
//
//import java.util.Random;
//
//import static org.hamcrest.Matchers.*;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//
//
//import static org.hamcrest.Matchers.*;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class GroupRestControllerTest {
//    MockMvc mockMvc;
//    @Mock
//    private GroupService groupService;
//
//    @InjectMocks
//    private GroupRestController groupRestController;
//
//    @Before
//    public void init() {
//        MockitoAnnotations.initMocks(this);
//        mockMvc = MockMvcBuilders
//                .standaloneSetup(groupRestController)
//                .build();
//    }
//
//
//    private Group getGroupExample() {
//        Group group = new Group("Grouppp",
//                "Interesting group",
//                true,
//                "uuid");
//        group.setId(new Random().nextLong());
//        return group;
//    }
//
//    @Test
//    public void showMessage_IfMessageFound_ShouldReturnFoundMessage() throws Exception {
//
//        Group group = getGroupExample();
//        GroupDto groupDto = new GroupDto(group);
//
//        when(groupService.getGroupById(group.getId())).thenReturn(group);
//
//        when(new GroupDto(Matchers.any(Group.class))).thenReturn(groupDto);
//
//        mockMvc.perform(get("/group/{id}", group.getId()))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
//                .andExpect(jsonPath("$.id", is(groupDto.getId())))
//                .andExpect(jsonPath("$.groupName", is(groupDto.getGroupName())))
//                .andExpect(jsonPath("$.description", is(groupDto.getDescription())))
//                .andExpect(jsonPath("$.isOpened", is(groupDto.isOpened())))
//                .andExpect(jsonPath("$.numberOfUsers", is(groupDto.getNumberOfUsers())));
//
//
//
//
//        verify(groupService, times(1)).getGroupById(group.getId());
//        verifyNoMoreInteractions(groupService);
//    }
//}
