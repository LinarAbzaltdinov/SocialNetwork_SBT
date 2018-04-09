package ru.sberbank.socialnetwork.users.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.socialnetwork.users.entities.User;
import ru.sberbank.socialnetwork.users.services.UserService;

import java.util.List;

@RestController
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
        User foundUser = userService.findUser(email);
        return foundUser;
    }

    @PostMapping("/create")
    public User createUser(@RequestParam("email") String email) {
        return userService.createUser(email);
    }
}
