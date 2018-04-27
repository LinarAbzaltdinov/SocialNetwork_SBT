package ru.sberbank.socialnetwork.users.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.sberbank.socialnetwork.users.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserRestController.class)
public class UserRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserRestController userRestController;

    private User getRandomUser() {
        String email = "test@test.com";
        String pass = "test";
        User user = new User(email, pass);
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setUuid(UUID.randomUUID().toString());
        return user;
    }

    @Test
    public void whenAddUser_ThenReturnUserUuid() throws Exception {
        User user = getRandomUser();

        given(userRestController.addUser(user.getEmail(), user.getPassword()))
                .willReturn(user.getUuid());

        mvc.perform(
                post("/users/create")
                        .param("email", user.getEmail())
                        .param("password", user.getPassword()))
                .andExpect(status().isOk())
                .andExpect(content().string(user.getUuid()));

        verify(userRestController, times(1))
                .addUser(user.getEmail(), user.getPassword());
        verifyNoMoreInteractions(userRestController);
    }

    @Test
    public void whenDeleteUser_ThenReturnTrue() throws Exception {
        User user = getRandomUser();

        given(userRestController.deleteUser(user.getUuid()))
                .willReturn(true);

        mvc.perform(delete("/users/{uuid}", user.getUuid()))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        verify(userRestController, times(1))
                .deleteUser(user.getUuid());
        verifyNoMoreInteractions(userRestController);
    }

    @Test
    public void whenGetAllUsers_ThenReturnListUsers() throws Exception {
        List<User> listUsers = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            listUsers.add(getRandomUser());
        }

        given(userRestController.getAllUsers())
                .willReturn(listUsers);

        mvc.perform(get("/users/all").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(listUsers.size())))
                .andExpect(jsonPath("$[*].listUsers", everyItem(is(listUsers))));

        verify(userRestController, times(1))
                .getAllUsers();
        verifyNoMoreInteractions(userRestController);
    }

    @Test
    public void whenGetUserByUuid_ThenUser() throws Exception {
        User mockUser = getRandomUser();
        String uuidMockUser = mockUser.getUuid();

        given(userRestController.getUser(uuidMockUser))
                .willReturn(mockUser);

        mvc.perform(get("/users/{uuid}", mockUser.getUuid()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.uuid", is(mockUser.getUuid())))
                .andExpect(jsonPath("$.email", is(mockUser.getEmail())))
                .andExpect(jsonPath("$.password", is(mockUser.getPassword())))
                .andExpect(jsonPath("$.firstName", is(mockUser.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(mockUser.getLastName())));

        verify(userRestController, Mockito.times(1))
                .getUser(uuidMockUser);
        verifyNoMoreInteractions(userRestController);
    }

    @Test
    public void whenGetUserByEmail_ThenUser() throws Exception {
        User mockUser = getRandomUser();
        String emailMockUser = mockUser.getEmail();

        given(userRestController.getUserByEmail(emailMockUser))
                .willReturn(mockUser);

        mvc.perform(post("/users/getByEmail").param("email", mockUser.getEmail()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.uuid", is(mockUser.getUuid())))
                .andExpect(jsonPath("$.email", is(mockUser.getEmail())))
                .andExpect(jsonPath("$.password", is(mockUser.getPassword())))
                .andExpect(jsonPath("$.firstName", is(mockUser.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(mockUser.getLastName())));

        verify(userRestController, Mockito.times(1))
                .getUserByEmail(emailMockUser);
        verifyNoMoreInteractions(userRestController);
    }

    @Test
    public void editUser() throws Exception {
       /* User updateMockUser = getRandomUser();
        updateMockUser.setLastName("NewLastName");

        given(userRestController.editUser(updateMockUser))
                .willReturn(updateMockUser);

        mvc.perform(put("/users/update"))
                //.andExpect(status().isOk())
                //.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.uuid", is(updateMockUser.getUuid())))
                .andExpect(jsonPath("$.email", is(updateMockUser.getEmail())))
                .andExpect(jsonPath("$.password", is(updateMockUser.getPassword())))
                .andExpect(jsonPath("$.firstName", is(updateMockUser.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(updateMockUser.getLastName())));

        verify(userRestController, Mockito.times(1))
                .editUser(updateMockUser);
        verifyNoMoreInteractions(userRestController);*/
    }

    @Test
    public void login() {
    }





    /*private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserRestController userRestController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(userRestController)
                .setControllerAdvice(new ResourceNotFoundExceptionHandler())
                .build();
    }

    User getMockUser() {
        String uuid = "1111";
        String email = "testmail@google.com";
        String password = "qwerty1234";
        *//*String firstName = "testName";
        String lastName = "testLastName";
        String photoId = "1";
        int sex = 1;
        LocalDate birthday = LocalDate.of(1990, 1, 22);*//*
        User user = new User(email, password);
        user.setUuid(uuid);
        *//*user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhotoId(photoId);
        user.setSex(sex);
        user.setBirthday(birthday);*//*
        return user;
    }*/

    /*@Test
    public void whenAddUserThenReturnUuidUser() throws Exception {
        UserDto mockUser = getMockUser();
        when(userService.addUser(mockUser.getEmail(), mockUser.getPassword()))
                .thenReturn(mockUser);

        mockMvc.perform(
                post("/create")
                        .param("email", mockUser.getEmail())
                        .param("password", mockUser.getPassword()))
                .andExpect(status().isOk())
                .andExpect(content().string(mockUser.getUuid()));

        verify(userService, times(1))
                .addUser(mockUser.getEmail(), mockUser.getPassword());
        verifyNoMoreInteractions(userService);
    }*/

    /*@Test
    public void deleteUser() throws Exception {
        String uuid = "111";
        when(userService.deleteUser(uuid)).thenReturn(true);

        mockMvc.perform(
                delete("/{uuid}", uuid))
                .andExpect(status().isOk());
    }*/


}