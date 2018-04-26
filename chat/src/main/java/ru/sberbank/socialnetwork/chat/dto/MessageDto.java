package ru.sberbank.socialnetwork.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Data
public class MessageDto {

    private String id;
    private String content;
    private String userId;
    private Long chatId;
    private Date createdDate;

    public MessageDto(String content, String userId, Long chatId) {
        this.content = content;
        this.userId = userId;
        this.chatId = chatId;
    }
}