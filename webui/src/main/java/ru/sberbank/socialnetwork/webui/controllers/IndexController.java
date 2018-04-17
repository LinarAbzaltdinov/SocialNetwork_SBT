package ru.sberbank.socialnetwork.webui.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IndexController {
    @GetMapping(value = {"/login", "/signup"})
    public String showLoginAndRegistrationForm(Model model) {
        return "login-signup";
    }

    @PostMapping("/login")
    public String signIn(Model model) {
        return "fragments";
    }

    @PostMapping("/signup")
    public String signUp(Model model) {
        return "fragments";
    }
}
