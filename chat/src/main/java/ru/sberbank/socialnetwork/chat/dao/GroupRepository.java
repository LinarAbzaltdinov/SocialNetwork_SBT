package ru.sberbank.socialnetwork.chat.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import ru.sberbank.socialnetwork.chat.entities.Group;

import java.util.Collection;

public interface GroupRepository extends Repository<Group, Long> {

    Group save(Group entity);

    Group findById(Long id);

    Collection<Group> findByGroupNameStartingWith(String prefix);

    Collection<Group> findByInvitedUserUuids(String uuid);
}
