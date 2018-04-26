package ru.sberbank.socialnetwork.message.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MessageDTO {
    String content;
    String userId;
    String chatId;
    String createdDate;
}
