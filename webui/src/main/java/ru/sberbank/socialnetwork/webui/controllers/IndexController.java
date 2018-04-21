package ru.sberbank.socialnetwork.webui.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.sberbank.socialnetwork.webui.models.UserDetails;
import ru.sberbank.socialnetwork.webui.models.UserInfo;
import ru.sberbank.socialnetwork.webui.services.UserAuthService;
import ru.sberbank.socialnetwork.webui.services.UserInfoService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class IndexController {

    public static final String ERROR_EMAIL_REGISTERED = "Данный email уже зарегистрирован";

    @Autowired
    UserAuthService userAuthService;

    @Autowired
    UserInfoService userInfoService;

    @GetMapping(value = {"/login", "/signup"})
    public String showLoginAndRegistrationForm(Model model) {
        model.addAttribute("user", new UserDetails());
        return "login-signup";
    }

    @PostMapping("/login")
    public String login(Model model, @ModelAttribute("user") UserDetails userDetails,
                        HttpServletResponse response) throws IOException {
        String authToken = userAuthService.login(userDetails);
        response.addHeader("Authorization", authToken);
        return "redirect:/groups";
    }

    @PostMapping("/signup")
    public String signUp(Model model, @ModelAttribute("user") UserDetails userDetails,
                         HttpServletResponse response) throws IOException {
        UserInfo userInfo = userInfoService.createUser(userDetails.getEmail());
        if (userInfo == null) {
            model.addAttribute("error", ERROR_EMAIL_REGISTERED);
            return "login-signup";
        }
        String authToken = userAuthService.login(userDetails);
        response.addHeader("Authorization", authToken);
        return "redirect:/groups";
    }
}
