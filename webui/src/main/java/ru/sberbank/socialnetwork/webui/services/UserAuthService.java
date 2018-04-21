package ru.sberbank.socialnetwork.webui.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sberbank.socialnetwork.webui.client.AuthServiceClient;
import ru.sberbank.socialnetwork.webui.client.UserServiceClient;
import ru.sberbank.socialnetwork.webui.models.UserDetails;
import ru.sberbank.socialnetwork.webui.models.UserInfo;

@Service
public class UserAuthService {

    @Autowired
    private AuthServiceClient authServiceClient;

    @Autowired
    private UserServiceClient userServiceClient;

    public String login(UserDetails userDetails) {
        return null;
    }
}
