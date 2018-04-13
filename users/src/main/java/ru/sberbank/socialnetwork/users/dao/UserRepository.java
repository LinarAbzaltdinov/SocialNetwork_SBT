package ru.sberbank.socialnetwork.users.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sberbank.socialnetwork.users.entities.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String email);

    List<User> findAll();

    User findByUuid(String uuid);

    //long getUserID(User user);


}
