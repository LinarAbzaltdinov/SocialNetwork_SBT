package ru.sberbank.socialnetwork.webui.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.socialnetwork.webui.models.Group;
import ru.sberbank.socialnetwork.webui.services.GroupService;

import java.util.List;

@Controller
@RequestMapping("/groups")
public class GroupController {
    @GetMapping("")
    public String showAllGroups(Model model, @CookieValue(HttpHeaders.AUTHORIZATION) String authToken) {
        List<Group> userGroups = GroupService.getGroups(authToken);
        model.addAttribute("myGroups", userGroups);
        model.addAttribute("allGroups", userGroups);
        return "groups";
    }

    @GetMapping("/new")
    public String newGroup(Model model, @CookieValue(HttpHeaders.AUTHORIZATION) String authToken) {
        model.addAttribute("group", new Group());
        return "groupPage";
    }

    @PostMapping("/new")
    public String createGroup(Model model, @ModelAttribute("group") Group group,
                              @CookieValue(HttpHeaders.AUTHORIZATION) String authToken) {
        Group createdGroup = GroupService.createGroup(authToken, group);
        return "redirect:/groups/" + createdGroup.getId();
    }

    @GetMapping("/{id}")
    public String showGroup(Model model, @CookieValue(HttpHeaders.AUTHORIZATION) String authToken) {
        List<Group> userGroups = GroupService.getGroups(authToken);
        model.addAttribute("groups", userGroups);
        return "groups";
    }
}
