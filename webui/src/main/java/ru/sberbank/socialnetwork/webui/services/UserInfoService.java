package ru.sberbank.socialnetwork.webui.services;

import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.sberbank.socialnetwork.webui.client.UserServiceClient;
import ru.sberbank.socialnetwork.webui.models.Credentials;
import ru.sberbank.socialnetwork.webui.models.UserInfo;

@Service
public class UserInfoService {

    private final UserServiceClient userServiceClient;

    @Autowired
    public UserInfoService(UserServiceClient userServiceClient) {
        this.userServiceClient = userServiceClient;
    }


    public boolean verify(Credentials credentials) {
        boolean isCredentialsValid = userServiceClient.login(credentials.getEmail(), credentials.getPassword());
        return isCredentialsValid;
    }

    public Boolean createUser(Credentials credentials) {
        try {
            userServiceClient.createUser(credentials.getEmail(), credentials.getPassword());
            return true;
        } catch (FeignException e) {
            return false;
        }
    }

    public UserInfo getUserByEmail(String email) {
        ResponseEntity<UserInfo> foundUser = userServiceClient.getUserByEmail(email);
        return foundUser.getStatusCode().is2xxSuccessful()
               ? foundUser.getBody()
               : null;
    }

    public void update(UserInfo user) {
        ResponseEntity<UserInfo> userInfoResponseEntity = userServiceClient.editUser(user);
    }

    public void updatePassword(String newPassword) {

    }

    public UserInfo getUser(String userId) {
        ResponseEntity<UserInfo> foundUser = userServiceClient.getUser(userId);
        return foundUser.getStatusCode().is2xxSuccessful()
               ? foundUser.getBody()
               : null;
    }
}
