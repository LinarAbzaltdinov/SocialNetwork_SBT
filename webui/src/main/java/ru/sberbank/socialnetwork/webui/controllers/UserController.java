package ru.sberbank.socialnetwork.webui.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.socialnetwork.webui.models.UserInfo;
import ru.sberbank.socialnetwork.webui.services.UserInfoService;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserInfoService userInfoService;

    @GetMapping("")
    public String showUserProfile(Model model) {
        return "user-profile";
    }

    @PostMapping("/updateInfo")
    public String updateProfile(@ModelAttribute UserInfo user) {
        userInfoService.update(user);
        return "redirect:/user";
    }

    @PostMapping("/updatePassword")
    public String updateProfile(@RequestParam("newPassword") String newPassword) {
        userInfoService.updatePassword(newPassword);
        return "redirect:/user";
    }
}
