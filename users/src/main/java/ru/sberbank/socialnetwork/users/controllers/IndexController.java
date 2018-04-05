package ru.sberbank.socialnetwork.users.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.sberbank.socialnetwork.users.dao.UserRepository;
import ru.sberbank.socialnetwork.users.entities.User;
import ru.sberbank.socialnetwork.users.services.UserRegitrationService;

import java.util.List;

@RestController
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "Hello!";
    }

//    @GetMapping("/users")
//    public List<User> getUsers(UserRepository userRepository) {
//        return userRepository.findAll();
//    }
//
//    @GetMapping("/{email}")
//    public User getUser(UserRegitrationService userRegitrationService, @PathVariable String email) {
//        return userRegitrationService.createUser(email);
//    }
}
