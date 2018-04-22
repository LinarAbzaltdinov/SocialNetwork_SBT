package ru.sberbank.socialnetwork.webui.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.sberbank.socialnetwork.webui.models.Credentials;
import ru.sberbank.socialnetwork.webui.models.UserInfo;
import ru.sberbank.socialnetwork.webui.services.UserAuthService;
import ru.sberbank.socialnetwork.webui.services.UserInfoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class IndexController {

    public static final String ERROR_EMAIL_REGISTERED = "Данный email уже зарегистрирован";
    public static final String ERROR_WRONG_CREDENTIALS = "Введен неправильный email или пароль";

    @Autowired
    UserAuthService authService;

    @Autowired
    UserInfoService userService;

    @GetMapping(value = {"/", "/index"})
    public String index(Model model, HttpServletRequest request) {
        String authToken = request.getHeader("Authorization");
        if (authToken == null || !authService.isValidToken(authToken)) {
            return "redirect:/login";
        } else {
            return "redirect:/groups";
        }
    }

    @GetMapping(value = "/login")
    public String showLoginForm(Model model) {
        model.addAttribute("formAction", "login");
        model.addAttribute("user", new Credentials());
        return "login-signup";
    }

    @GetMapping(value = "/signup")
    public String showRegistrationForm(Model model) {
        model.addAttribute("formAction", "signup");
        model.addAttribute("user", new Credentials());
        return "login-signup";
    }

    @PostMapping("/login")
    public String login(Model model, @ModelAttribute("user") Credentials credentials,
                        HttpServletResponse response) throws IOException {
        boolean isCredentialsAccepted = userService.verify(credentials);
        if (!isCredentialsAccepted) {
            model.addAttribute("error", ERROR_WRONG_CREDENTIALS);
            return "login-signup";
        }
        String authToken = authService.login(credentials);
        response.addHeader("Authorization", authToken);
        return "redirect:/groups";
    }

    @PostMapping("/signup")
    public String signUp(Model model, @ModelAttribute("user") Credentials credentials,
                         HttpServletResponse response) throws IOException {
        UserInfo userInfo = userService.createUser(credentials);
        if (userInfo == null) {
            model.addAttribute("error", ERROR_EMAIL_REGISTERED);
            return "login-signup";
        }
        String authToken = authService.login(credentials);
        response.addHeader("Authorization", authToken);
        return "redirect:/user";
    }
}
