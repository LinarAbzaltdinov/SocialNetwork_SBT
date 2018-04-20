package ru.sberbank.socialnetwork.message.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    String id;
    String content;
    String userId;
    String chatId;
    String createdDate;
}
