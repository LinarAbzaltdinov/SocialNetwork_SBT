package ru.sberbank.socialnetwork.users.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sberbank.socialnetwork.users.dao.UserRepository;
import ru.sberbank.socialnetwork.users.entities.User;
import ru.sberbank.socialnetwork.users.services.UserService;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public User addUser(String email, String password) {
        User newUser = new User(email, password);
        return userRepository.saveAndFlush(newUser);
    }

    @Transactional(readOnly = true)
    @Override
    public User findUserByUuid(String uuid) {
        return userRepository.findByUuid(uuid);
    }

    @Transactional
    @Override
    public boolean deleteUser(String uuid) {
        if (userRepository.exists(uuid)) {
            userRepository.delete(uuid);
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public User editUser(User oldUser) {
        User model = oldUser;
        if (model.getUuid() == null) {
            model.setUuid(UUID.randomUUID().toString());
        }
        return userRepository.saveAndFlush(model);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public User findUserByEmail(String email) {
        User foundUser = userRepository.findByEmail(email);
        return foundUser;
    }
}
