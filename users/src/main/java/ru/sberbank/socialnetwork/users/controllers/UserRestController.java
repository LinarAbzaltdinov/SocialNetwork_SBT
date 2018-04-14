package ru.sberbank.socialnetwork.users.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.socialnetwork.users.entities.User;
import ru.sberbank.socialnetwork.users.services.UserService;

import java.util.List;

@RestController
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public String addUser(@RequestParam String email,
                          @RequestParam String password) {
        User addUser = new User(email, password);
        User createdUser = userService.addUser(addUser);
        return createdUser.getUuid();
    }

    @DeleteMapping("/users/{uuid}")
    public boolean deleteUser(@PathVariable String uuid) {
        return userService.deleteUser(uuid);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(value = "/users/{uuid}", produces = "application/json")
    public User getUser(@PathVariable String uuid) {
        return userService.findUserByUuid(uuid);
    }

    /*@PutMapping(value = "/users/{uuid}")
    public User editUser(@PathVariable("uuid") String uuid) {
        User foundedUser = userService.findUserByUuid(uuid);
        return userService.editUser(foundedUser);
    }*/
}
