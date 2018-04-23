package ru.sberbank.socialnetwork.webui.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sberbank.socialnetwork.webui.client.UserServiceClient;
import ru.sberbank.socialnetwork.webui.models.Credentials;
import ru.sberbank.socialnetwork.webui.models.UserInfo;

@Service
public class UserInfoService {
//    @Autowired
//    UserServiceClient userServiceClient;

    UserInfo user = new UserInfo();

    public boolean verify(Credentials credentials) {
        return true;
    }

    public UserInfo createUser(Credentials credentials) {
        user.setEmail(credentials.getEmail());
        return user;
    }

    public UserInfo getUser(String authToken) {
        return user;
    }

    public void update(UserInfo user) {
        this.user = user;
    }
}
