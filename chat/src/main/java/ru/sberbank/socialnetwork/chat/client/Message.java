package ru.sberbank.socialnetwork.chat.client;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class Message {
    private String messageId;
    private String content;
    private String userId;
    private Date date;
}
