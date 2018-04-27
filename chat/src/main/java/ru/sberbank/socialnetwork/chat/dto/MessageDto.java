package ru.sberbank.socialnetwork.chat.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
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