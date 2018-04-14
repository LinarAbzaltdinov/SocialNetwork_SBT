package ru.sberbank.socialnetwork.webui.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @GetMapping("/user")
    public String showUserProfile() {
        return "user-profile";
    }
}
