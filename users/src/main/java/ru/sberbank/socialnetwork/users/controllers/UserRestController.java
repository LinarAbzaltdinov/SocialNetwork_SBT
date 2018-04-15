package ru.sberbank.socialnetwork.users.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.socialnetwork.users.entities.User;
import ru.sberbank.socialnetwork.users.services.UserService;

import java.util.List;

import static ru.sberbank.socialnetwork.users.utils.PasswordStorage.*;

@RestController
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public String addUser(@RequestParam String email,
                          @RequestParam String password) throws CannotPerformOperationException {
        String hashPass = createHash(password);
        User createdUser = userService.addUser(email, hashPass);
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

    @PostMapping("/login")
    public boolean login(@RequestParam String email,
                         @RequestParam String password)
            throws InvalidHashException, CannotPerformOperationException {
        String hash = userService.findUserByEmail(email).getPassword();
        return verifyPassword(password, hash);
    }
}
