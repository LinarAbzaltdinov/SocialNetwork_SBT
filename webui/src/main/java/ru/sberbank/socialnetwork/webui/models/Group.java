package ru.sberbank.socialnetwork.webui.models;

import lombok.Data;

@Data
public class Group {
    private String id;
    private String groupName;
    private String description;
    private boolean isOpened;
    private Integer numberOfUsers;
}
