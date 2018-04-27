package ru.sberbank.socialnetwork.webui.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Message {
    private String chatId;
    private String userId;
    private String content;
    private String createdDate;

    public Message(String chatId, String userId, String content) {
        this.chatId = chatId;
        this.userId = userId;
        this.content = content;
    }
}
