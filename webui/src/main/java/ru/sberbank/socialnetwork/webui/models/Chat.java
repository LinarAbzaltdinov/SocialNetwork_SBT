package ru.sberbank.socialnetwork.webui.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Chat {
    private String id;
    private String chatName;
    private String creatorId;
    private String groupId;
    private boolean isOpened = true;
    List<String> userList = new ArrayList<>();
}
