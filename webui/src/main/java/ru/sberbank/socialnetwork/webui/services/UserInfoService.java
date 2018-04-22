package ru.sberbank.socialnetwork.webui.services;

import org.springframework.stereotype.Service;
import ru.sberbank.socialnetwork.webui.models.Credentials;
import ru.sberbank.socialnetwork.webui.models.UserInfo;

@Service
public class UserInfoService {
    public boolean verify(Credentials credentials) {
        return false;
    }

    public UserInfo createUser(Credentials credentials) {
        return new UserInfo();
    }
}
