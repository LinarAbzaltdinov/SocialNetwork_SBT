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

    private static final String SESSION_ATTR_USER = "userId";
    private final GroupService groupService;
    private final UserInfoService userInfoService;

    @Autowired
    public GroupController(GroupService groupService, UserInfoService userInfoService) {
        this.groupService = groupService;
        this.userInfoService = userInfoService;
    }

    @GetMapping("")
    public String showAllGroups(Model model, @SessionAttribute(SESSION_ATTR_USER) String userId) {
        List<Group> userGroups = groupService.getGroups(userId);
        model.addAttribute("myGroups", userGroups);
        List<Group> allGroups = groupService.getAllGroups(userId);
        model.addAttribute("allGroups", allGroups);
        return "groups";
    }

    @GetMapping("/new")
    public String newGroup(Model model, @SessionAttribute(SESSION_ATTR_USER) String userId) {
        Group newGroup = new Group();
        model.addAttribute("group", newGroup);
        return "newPage";
    }

    @PostMapping("/new")
    public String createGroup(Model model, @ModelAttribute("group") Group group,
                              @SessionAttribute(SESSION_ATTR_USER) String userId) {
        Group createdGroup = groupService.createGroup(userId, group);
        return "redirect:/groups/" + createdGroup.getId();
    }

    @GetMapping("/{id}")
    public String showGroup(Model model, @CookieValue(HttpHeaders.AUTHORIZATION) String authToken) {
        return "chats";
    }
}
