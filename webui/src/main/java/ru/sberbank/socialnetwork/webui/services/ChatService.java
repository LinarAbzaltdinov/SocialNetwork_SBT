package ru.sberbank.socialnetwork.webui.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    public ResponseEntity createMessage(String chatId, String messageText) {
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
