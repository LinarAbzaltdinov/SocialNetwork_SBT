package ru.sberbank.socialnetwork.webui.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sberbank.socialnetwork.webui.client.AuthServiceClient;
import ru.sberbank.socialnetwork.webui.client.UserServiceClient;
import ru.sberbank.socialnetwork.webui.models.Credentials;

@Service
public class UserAuthService {
//
//    @Autowired
//    private AuthServiceClient authServiceClient;

    @Autowired
    private UserServiceClient userServiceClient;

    public String login(Credentials credentials) {
        return "123123";
    }

    public boolean isValidToken(String authToken) {
        return true;
    }
}
