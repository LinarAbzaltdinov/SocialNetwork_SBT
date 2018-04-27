package ru.sberbank.socialnetwork.webui.models;

import lombok.Data;

@Data
public class UserInfo {
    private String uuid;
    private String email;
    private String firstName = "";
    private String lastName = "";
    private String birthday;

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
