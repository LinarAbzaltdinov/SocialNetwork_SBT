package ru.sberbank.socialnetwork.webui.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.sberbank.socialnetwork.webui.models.Credentials;
import ru.sberbank.socialnetwork.webui.models.UserInfo;
import ru.sberbank.socialnetwork.webui.services.UserInfoService;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class IndexController {

    public static final String ERROR_EMAIL_EXIST = "Данный email уже зарегистрирован";
    public static final String ERROR_WRONG_CREDENTIALS = "Введен неправильный email или пароль";

    private final UserInfoService userService;

    @Autowired
    public IndexController(UserInfoService userService) {
        this.userService = userService;
    }

    @GetMapping(value = {"/", "/index"})
    public String index(HttpSession httpSession) {
        Object sessionUserId = httpSession.getAttribute("userId");
        if (sessionUserId == null) {
            return "redirect:/login";
        } else {
            return "redirect:/groups";
        }
    }

    @GetMapping(value = "/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new Credentials());
        return "login-signup";
    }

    @GetMapping(value = "/signup")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new Credentials());
        return "login-signup";
    }

    @PostMapping("/login")
    public String login(Model model, @ModelAttribute("user") Credentials credentials,
                        HttpSession httpSession) throws IOException {
        boolean isCredentialsAccepted = userService.verify(credentials);
        if (!isCredentialsAccepted) {
            model.addAttribute("error", ERROR_WRONG_CREDENTIALS);
            return "login-signup";
        }
        addUserIdToSession(credentials, httpSession);
        return "redirect:/groups";
    }

    @PostMapping("/signup")
    public String signUp(Model model, @ModelAttribute("user") Credentials credentials,
                         HttpSession httpSession) throws IOException {
        boolean isUserCreated = userService.createUser(credentials);
        if (!isUserCreated) {
            model.addAttribute("error", ERROR_EMAIL_EXIST);
            return "login-signup";
        }
        addUserIdToSession(credentials, httpSession);
        return "redirect:/user";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/login?logout";
    }

    private void addUserIdToSession(Credentials credentials, HttpSession httpSession) {
        UserInfo user = userService.getUserByEmail(credentials.getEmail());
        httpSession.setAttribute("userId", user.getUuid());
    }
}
