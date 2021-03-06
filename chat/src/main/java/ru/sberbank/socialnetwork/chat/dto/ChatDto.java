package ru.sberbank.socialnetwork.chat.dto;

import lombok.*;
import ru.sberbank.socialnetwork.chat.entities.Chat;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class ChatDto {
    private Long id;
    private String chatName;
    private String creatorId;
    private Long groupId;
    private boolean isOpened;

    public ChatDto(Chat chat) {
        this.id = chat.getId();
        this.chatName = chat.getChatName();
        this.creatorId = chat.getCreatorId();
        this.groupId = chat.getGroup().getId();
        this.isOpened = chat.isOpened();
    }
}
