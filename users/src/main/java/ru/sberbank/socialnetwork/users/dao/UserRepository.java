package ru.sberbank.socialnetwork.users.dao;

import org.springframework.data.repository.Repository;
import ru.sberbank.socialnetwork.users.entities.User;

import java.util.List;

public interface UserRepository extends Repository<User, Long> {
    User findFirstByEmail(String email);
    List<User> findAll();
}
