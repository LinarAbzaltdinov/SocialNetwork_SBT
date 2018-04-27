package ru.sberbank.socialnetwork.chat.dto;

import lombok.*;
import ru.sberbank.socialnetwork.chat.entities.Group;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GroupDto {
    private Long id;
    private String groupName;
    private String description;
    private boolean isOpened;
    private Integer numberOfUsers;
    public GroupDto(Group group) {
        this.id = group.getId();
        this.groupName = group.getGroupName();
        this.description = group.getDescription();
        this.isOpened = group.isOpened();
        this.numberOfUsers = group.getUsers().size();
    }
}
