package ru.sberbank.socialnetwork.chat.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import ru.sberbank.socialnetwork.chat.entities.Group;

import java.util.Collection;
import java.util.List;

public interface GroupRepository extends Repository<Group, Long> {

    Group save(Group entity);

    Group findFirstById(Long id);

    @Query("select g from Group g left join g.users u where u.uuid = :uuid")
    List<Group> findByUsersUuid(@Param("uuid") String uuid);

    List<Group> findByGroupNameStartingWith(String prefix);

    List<Group> findByInvitedUserUuids(String uuid);

    List<Group> findAllByIsOpenedTrue();
}
