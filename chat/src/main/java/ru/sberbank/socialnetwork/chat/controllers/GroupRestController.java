package ru.sberbank.socialnetwork.chat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.socialnetwork.chat.dto.GroupDto;
import ru.sberbank.socialnetwork.chat.dto.UserDto;
import ru.sberbank.socialnetwork.chat.entities.Group;
import ru.sberbank.socialnetwork.chat.entities.GroupUser;
import ru.sberbank.socialnetwork.chat.entities.UserAccessMode;
import ru.sberbank.socialnetwork.chat.services.GroupService;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class GroupRestController {

    private final GroupService groupService;

    @Autowired
    public GroupRestController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping("/group/create")
    public GroupDto createChat(@RequestParam String groupName,
                               @RequestParam String description,
                               @RequestParam boolean isOpened,
                               @RequestParam String creatorId) {
        return new GroupDto(groupService.createGroup( groupName,  description,  isOpened,  creatorId));
    }

    @GetMapping("/group/user/{uuid}")
    public List<GroupDto> getUserGroups(@PathVariable String uuid) {
        return groupService.getUserGroups(uuid)
                .stream()
                .map(GroupDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/group/{groupId}")
    public GroupDto getGroupById(@PathVariable Long groupId) {
        return new GroupDto(groupService.getGroupById(groupId));
    }

    @GetMapping("/group/prefix/{prefix}")
    public Collection<GroupDto> getGroupsByPrefix(@PathVariable String prefix) {
        return groupService.getGroupsByPrefix(prefix)
                .stream()
                .map(GroupDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/group/{groupId}/users")
    public Collection<UserDto> getGroupUsers(@PathVariable Long groupId) {
        return groupService.getGroupUsers(groupId)
                .stream()
                .map(UserDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/group/user/{uuid}/invites")
    public Collection<GroupDto> getUserGroupInvites(@PathVariable String uuid) {
        return groupService.getUserGroupInvites(uuid)
                .stream()
                .map(GroupDto::new)
                .collect(Collectors.toList());
    }

    @PostMapping("/group/{groupId}/user/{uuid}/invite")
    public GroupDto inviteUser(@PathVariable Long groupId, @PathVariable String uuid) {
        return new GroupDto(groupService.inviteUser(groupId, uuid));
    }

    @PostMapping("/group/{groupId}/user/{uuid}/invite/accept")
    public GroupDto acceptInvite(@PathVariable Long groupId, @PathVariable String uuid) {
        return new GroupDto(groupService.acceptInvite(groupId, uuid));
    }

    @PostMapping("/group/{groupId}/user/{uuid}/invite/deny")
    public GroupDto denyInvite(@PathVariable Long groupId, @PathVariable String uuid) {
        return new GroupDto(groupService.denyInvite(groupId, uuid));
    }

    @PostMapping("/group/{groupId}/user/{uuid}/add")
    public GroupDto addUser(@PathVariable Long groupId,
                         @PathVariable String uuid,
                         @RequestParam Integer mode) {
        return new GroupDto(groupService.addUser(groupId, uuid, UserAccessMode.values()[mode]));
    }

    @PostMapping("/group/{groupId}/user/{uuid}/remove")
    public GroupDto removeUser(@PathVariable Long groupId,
                         @PathVariable String uuid) {
        return new GroupDto(groupService.removeUser(groupId, uuid));
    }

    @PostMapping("/group/{groupId}/change")
    public GroupDto changeGroupParameters(@PathVariable Long groupId,
                                       @RequestParam String groupName,
                                       @RequestParam  String description,
                                       @RequestParam  boolean isOpened) {
        return new GroupDto(groupService.changeGroupParameters(groupId, groupName, description, isOpened));
    }
}
