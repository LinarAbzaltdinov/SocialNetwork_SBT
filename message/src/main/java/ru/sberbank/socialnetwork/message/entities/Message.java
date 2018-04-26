package ru.sberbank.socialnetwork.message.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Document(collection = "message")
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Message {
    @Id
    private String id;

    @NonNull
    private String content;

    @NonNull
    private String userId;

    @NonNull
    private String chatId;

    private LocalDateTime createdDate = LocalDateTime.now();
}
