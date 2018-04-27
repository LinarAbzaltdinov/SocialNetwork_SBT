package ru.sberbank.socialnetwork.chat.dto;

import lombok.*;
import ru.sberbank.socialnetwork.chat.entities.GroupUser;
import ru.sberbank.socialnetwork.chat.entities.UserAccessMode;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String uuid;
    private UserAccessMode accessMode;
    public UserDto(GroupUser user) {
        this.uuid = user.getUuid();
        this.accessMode = user.getAccessMode();
    }
}
