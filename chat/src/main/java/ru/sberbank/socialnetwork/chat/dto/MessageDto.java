package ru.sberbank.socialnetwork.chat.dto;

import lombok.AllArgsConstructor;

import java.util.Date;

@AllArgsConstructor
public class MessageDto {

    private String id;
    private String content;
    private String userId;
    private Long chatId;
    private Date createdDate;

}