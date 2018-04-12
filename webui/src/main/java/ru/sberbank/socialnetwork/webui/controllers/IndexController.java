package ru.sberbank.socialnetwork.webui.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/login")
    public String index(Model model) {

        return "login";
    }
}
