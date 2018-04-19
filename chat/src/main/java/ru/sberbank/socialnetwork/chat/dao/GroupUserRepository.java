package ru.sberbank.socialnetwork.chat.dao;

import org.springframework.data.repository.Repository;
import ru.sberbank.socialnetwork.chat.entities.Group;
import ru.sberbank.socialnetwork.chat.entities.GroupUser;



public interface GroupUserRepository extends Repository<GroupUser, Long> {

    void save(GroupUser entity);

    GroupUser findByGroupAndUuid(Group group, String uuid);
}
