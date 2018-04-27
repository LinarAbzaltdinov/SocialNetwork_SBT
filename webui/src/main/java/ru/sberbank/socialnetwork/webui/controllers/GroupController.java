package ru.sberbank.socialnetwork.webui.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.socialnetwork.webui.models.Chat;
import ru.sberbank.socialnetwork.webui.models.Group;
import ru.sberbank.socialnetwork.webui.services.GroupService;
import ru.sberbank.socialnetwork.webui.services.UserInfoService;

import java.util.Collection;
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
        Collection<Group> userGroups = groupService.getGroups(userId);
        model.addAttribute("myGroups", userGroups);
        Collection<Group> allGroups = groupService.getAllOpenedGroups(userId);
        model.addAttribute("allGroups", allGroups);
        Collection<Group> createdByMeGroups = groupService.getGroupsCreatedByMe(userId);
        model.addAttribute("createdByMeGroups", createdByMeGroups);
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

    @GetMapping("/{groupId}")
    public String showGroup(Model model, @PathVariable("groupId") String groupId,
                            @SessionAttribute(SESSION_ATTR_USER) String userId) {
        List<Chat> chats = groupService.chatOfGroupAndUser(groupId, userId);
        model.addAttribute("chats", chats);
        boolean isUserCreatorOfGroup = groupService.isUserCreatorOfGroup(userId, groupId);
        model.addAttribute("isUserCreatorOfGroup", isUserCreatorOfGroup);
        return "chats";
    }

    @PostMapping("/{groupId}/addUser")
    @ResponseStatus(HttpStatus.OK)
    public void addUserToGroup(@PathVariable("groupId") String groupId,
                               @SessionAttribute(SESSION_ATTR_USER) String userId) {
        groupService.addUserToGroup(userId, groupId);
    }

    @DeleteMapping("/{groupId}/removeUser")
    @ResponseStatus(HttpStatus.OK)
    public void removeUserFromGroup(@PathVariable("groupId") String groupId,
                                    @SessionAttribute(SESSION_ATTR_USER) String userId) {
        groupService.removeUserFromGroup(userId, groupId);
    }

    @DeleteMapping("/{groupId}")
    @ResponseStatus(HttpStatus.OK)
    public void removeGroup(@PathVariable("groupId") String groupId) {
        groupService.removeGroup(groupId);
    }
}
