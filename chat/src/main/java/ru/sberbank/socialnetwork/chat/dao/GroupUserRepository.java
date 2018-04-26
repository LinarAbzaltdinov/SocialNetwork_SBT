package ru.sberbank.socialnetwork.chat.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import ru.sberbank.socialnetwork.chat.entities.Group;
import ru.sberbank.socialnetwork.chat.entities.GroupUser;

import java.util.Collection;
import java.util.List;


public interface GroupUserRepository extends Repository<GroupUser, Long> {

    void save(GroupUser entity);

    GroupUser findByGroupAndUuid(Group group, String uuid);
}
