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
    public String addUser(@RequestParam("email") String email,
                          @RequestParam("password") String password) {
        User createdUser = userService.addUser(email, password);
        return createdUser.getUuid();
    }

    @DeleteMapping("/{uuid}")
    public boolean deleteUser(@PathVariable("uuid") String uuid) {
        return userService.deleteUser(uuid);
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(value = "/{uuid}", produces = "application/json")
    public User getUser(@PathVariable("uuid") String uuid) {
        return userService.findUserByUuid(uuid);
    }

    @PostMapping(value = "/getByEmail", produces = "application/json")
    public User getUserByEmail(@RequestParam("email") String email) {
        return userService.findUserByEmail(email);
    }

    @PutMapping(value = "/update")
    public User editUser(@RequestBody User updatedUser) {
        User foundedUser = userService.editUser(updatedUser);
        return userService.editUser(foundedUser);
    }

    @PostMapping("/login")
    public boolean login(@RequestParam("email") String email,
                         @RequestParam("password") String password) {
        return userService.login(email, password);
    }
}
