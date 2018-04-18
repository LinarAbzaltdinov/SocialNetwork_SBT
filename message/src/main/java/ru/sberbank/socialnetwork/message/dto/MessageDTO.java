package ru.sberbank.socialnetwork.message.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDTO {
    String id;
    String content;
    String userId;
    String chatId;
    String createdDate;
}
