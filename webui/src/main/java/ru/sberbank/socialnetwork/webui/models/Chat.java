package ru.sberbank.socialnetwork.webui.models;

import lombok.Data;

@Data
public class Chat {
    private String id;
    private String chatName;
    private String creatorId;
    private String groupId;
    private boolean isOpened = true;
}
