package ru.sberbank.socialnetwork.webui.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MessageForView {
    private String content;
    private UserInfo user;
    private String createdDateTime;
}
