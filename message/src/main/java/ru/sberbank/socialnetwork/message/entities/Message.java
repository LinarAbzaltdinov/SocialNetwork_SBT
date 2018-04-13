package ru.sberbank.socialnetwork.message.entities;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "message")
@RequiredArgsConstructor
@Getter @Setter
public class Message {
    @Id
    private String id;

    @NonNull
    private String content;

    private Date created = new Date();
}
