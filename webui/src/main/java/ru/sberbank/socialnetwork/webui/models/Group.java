package ru.sberbank.socialnetwork.webui.models;

import lombok.Data;

@Data
public class Group {
    private String id;
    private String groupName = "";
    private int amount = 1;
    private boolean isOpened = true;
    private String description;
}
