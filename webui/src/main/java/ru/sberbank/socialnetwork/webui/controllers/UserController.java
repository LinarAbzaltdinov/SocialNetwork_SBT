package ru.sberbank.socialnetwork.webui.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.socialnetwork.webui.client.UserServiceClient;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class UserController {

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private UserServiceClient userServiceClient;

    @GetMapping("/profile")
    public String showUserProfile(@SessionAttribute("userId") String userId, Model model) throws IOException {
        String userJsonString = userServiceClient.getUser(userId);
        JsonNode userJsonNode = mapper.readTree(userJsonString);
        String user = userJsonNode.asText();
        model.addAttribute("email", user);
        return "user-profile";
    }

    @GetMapping(value = {"/login", "/signup"})
    public String showLoginAndRegistrationForm(Model model) {
        return "login-signup";
    }

    @PostMapping("/login")
    public String login(Model model) {
        return "fragments";
    }

    @PostMapping("/signup")
    public String signup(Model model,
                         @RequestParam String email,
                         @RequestParam String password,
                         HttpSession httpSession) throws IOException {
        String userId = userServiceClient.addUser(email, password);
        if (userId != null) {
            httpSession.setAttribute("userId", userId);
            return "redirect:/profile";
        } else {
            model.addAttribute("error", "Fail :(");
            return "login-signup";
        }
    }

}
