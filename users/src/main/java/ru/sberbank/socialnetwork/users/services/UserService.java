package ru.sberbank.socialnetwork.users.services;

import ru.sberbank.socialnetwork.users.entities.User;

import java.util.List;

public interface UserService {

    User addUser(String email, String password);

    boolean deleteUser(String uuid);

    List<User> getAllUsers();

    User editUser(User oldUser);

    User findUserByUuid(String uuid);

    User findUserByEmail(String email);
}
