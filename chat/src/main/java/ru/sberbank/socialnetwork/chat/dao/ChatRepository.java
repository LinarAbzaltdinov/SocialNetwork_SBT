package ru.sberbank.socialnetwork.chat.dao;

import ru.sberbank.socialnetwork.chat.entities.Chat;
import org.springframework.data.repository.Repository;
import ru.sberbank.socialnetwork.chat.entities.Group;

import java.util.Collection;


public interface ChatRepository extends Repository<Chat, Long> {

    Chat save(Chat entity);

    Chat findById(Long id);

    Collection<Chat> findByUserUuidsAndGroup(String uuid, Group group);

    Collection<Chat> findByGroup(Group group);
}
