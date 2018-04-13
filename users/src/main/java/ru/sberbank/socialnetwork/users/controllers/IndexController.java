package ru.sberbank.socialnetwork.users.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.socialnetwork.users.entities.User;
import ru.sberbank.socialnetwork.users.services.UserService;

import java.util.List;

@RestController
@Controller
@ResponseBody
public class IndexController {

    private final UserService userService;

    @Autowired
    public IndexController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String index() {
        return "Hello!";
    }

    @GetMapping("/user")
    public User findUser(@RequestParam("email") String email) {
        return userService.findUser(email);
    }

    @PostMapping("/create")
    public String createUser(@RequestParam("email") String email,
                             @RequestParam("password") String password) {
        return userService.createUser(email, password).getUuid();
    }

    /**
     * Возвращает информацию о пользователе.
     * //todo: firstName, lastName, birthday, sex, photo
     *
     * @param uuid пользователя
     * @return сущность юзер.
     */
    @GetMapping(value = "/users/{uuid}", produces = "application/json")
    public User getUser(@PathVariable("uuid") String uuid) {
        return userService.findUserByUuid(uuid);
    }

    /**
     * Удаление пользователя.
     *
     * @param uuid пользователя.
     * @return true если удаление успешно, иначе false.
     */
    @DeleteMapping("/users/{uuid}")
    public boolean deleteUser(@PathVariable("uuid") String uuid) {
        return userService.deleteUser(uuid);
    }

    @GetMapping(value = "/users/all", produces = "application/json")
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

}
