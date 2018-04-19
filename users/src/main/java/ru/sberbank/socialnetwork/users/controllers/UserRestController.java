package ru.sberbank.socialnetwork.users.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.socialnetwork.users.entities.User;
import ru.sberbank.socialnetwork.users.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public String addUser(@RequestParam String email,
                          @RequestParam String password) {
        User createdUser = userService.addUser(email, password);
        return createdUser.getUuid();
    }

    @DeleteMapping("/{uuid}")
    public boolean deleteUser(@PathVariable String uuid) {
        return userService.deleteUser(uuid);
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(value = "/{uuid}", produces = "application/json")
    public User getUser(@PathVariable String uuid) {
        return userService.findUserByUuid(uuid);
    }

    @PutMapping(value = "/update")
    public User editUser(@RequestParam User updatedUser) {
        User foundedUser = userService.editUser(updatedUser);
        return userService.editUser(foundedUser);
    }

    @PostMapping("/login")
    public boolean login(@RequestParam String email,
                         @RequestParam String password) {
        return userService.login(email, password);
    }
}
