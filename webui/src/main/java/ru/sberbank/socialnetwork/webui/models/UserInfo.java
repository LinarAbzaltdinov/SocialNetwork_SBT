package ru.sberbank.socialnetwork.webui.models;

import lombok.Data;

@Data
public class UserInfo {
    private String id;
    private String email;
    private String firstName = "";
    private String lastName = "";
    private String birthDate = "";

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
