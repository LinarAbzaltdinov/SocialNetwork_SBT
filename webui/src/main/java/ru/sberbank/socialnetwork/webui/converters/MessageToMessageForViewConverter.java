package ru.sberbank.socialnetwork.webui.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.sberbank.socialnetwork.webui.client.UserServiceClient;
import ru.sberbank.socialnetwork.webui.models.Message;
import ru.sberbank.socialnetwork.webui.models.MessageForView;
import ru.sberbank.socialnetwork.webui.models.UserInfo;

@Component
public class MessageToMessageForViewConverter {

    private final UserServiceClient userServiceClient;

    @Autowired
    public MessageToMessageForViewConverter(UserServiceClient userServiceClient) {
        this.userServiceClient = userServiceClient;
    }

    public MessageForView convert(Message sourceMessage) {
        String userId = sourceMessage.getUserId();
        ResponseEntity<UserInfo> userInfoResponseEntity = userServiceClient.getUser(userId);
        if (!userInfoResponseEntity.getStatusCode().is2xxSuccessful()) {
            return null;
        }
        UserInfo user = userInfoResponseEntity.getBody();
        return new MessageForView(sourceMessage.getContent(), user, sourceMessage.getCreatedDate());
    }
}
