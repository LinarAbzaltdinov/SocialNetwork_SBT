package ru.sberbank.socialnetwork.message.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.sberbank.socialnetwork.message.entities.Message;

import java.util.List;

@Repository
public interface MessageRepository extends MongoRepository<Message, String> {
    List<Message> findByChatIdOrderByCreatedDateDesc(String chatId);

    List<Message> findByUserId(String userId);
}