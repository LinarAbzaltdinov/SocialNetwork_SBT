package ru.sberbank.socialnetwork.chat.dto;

import lombok.AllArgsConstructor;

import lombok.Data;
import ru.sberbank.socialnetwork.chat.entities.Chat;

import java.util.Collection;

@Data
public class ChatDto {
    private Long id;
    private String chatName;
    private String creatorId;
    private Long groupId;
    private Collection<MessageDto> messages;

    public ChatDto(Chat chat) {
        this.id = chat.getId();
        this.chatName = chat.getChatName();
        this.creatorId = chat.getCreatorId();
        this.groupId = chat.getGroup().getId();
        this.messages = null;
    }
}
