package ru.sberbank.socialnetwork.chat.dto;

import lombok.AllArgsConstructor;
import ru.sberbank.socialnetwork.chat.entities.GroupUser;
import ru.sberbank.socialnetwork.chat.entities.UserAccessMode;

@AllArgsConstructor
public class UserDto {
    private String uuid;
    private UserAccessMode accessMode;
    public UserDto(GroupUser user) {
        this.uuid = user.getUuid();
        this.accessMode = user.getAccessMode();
    }
}
