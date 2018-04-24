package ru.sberbank.socialnetwork.webui.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.socialnetwork.webui.models.Group;
import ru.sberbank.socialnetwork.webui.models.UserInfo;
import ru.sberbank.socialnetwork.webui.services.GroupService;
import ru.sberbank.socialnetwork.webui.services.UserInfoService;

import java.util.List;

@Controller
@RequestMapping("/groups")
public class GroupController {
    private final GroupService groupService;
    private final UserInfoService userInfoService;

    @Autowired
    public GroupController(GroupService groupService, UserInfoService userInfoService) {
        this.groupService = groupService;
        this.userInfoService = userInfoService;
    }

    @GetMapping("")
    public String showAllGroups(Model model, @SessionAttribute("email") String userEmail) {
        UserInfo user = userInfoService.getUserByEmail(userEmail);
        String userGroups = groupService.getGroups(user.getUuid());
        model.addAttribute("myGroups", userGroups);
        List<Group> allGroups = groupService.getAllGroups(user.getUuid());
        model.addAttribute("allGroups", userGroups);
        return "groups";
    }

    @GetMapping("/new")
    public String newGroup(Model model, @CookieValue(HttpHeaders.AUTHORIZATION) String authToken) {
        model.addAttribute("group", new Group());
        return "newPage";
    }

    @PostMapping("/new")
    public String createGroup(Model model, @ModelAttribute("group") Group group,
                              @CookieValue(HttpHeaders.AUTHORIZATION) String authToken) {
        Group createdGroup = GroupService.createGroup(authToken, group);
        return "redirect:/groups/" + createdGroup.getId();
    }

    @GetMapping("/{id}")
    public String showGroup(Model model, @CookieValue(HttpHeaders.AUTHORIZATION) String authToken) {
        return "chats";
    }
}
