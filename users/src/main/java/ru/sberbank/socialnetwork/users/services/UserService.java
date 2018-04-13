package ru.sberbank.socialnetwork.users.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sberbank.socialnetwork.users.dao.UserRepository;
import ru.sberbank.socialnetwork.users.entities.User;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User createUser(String email, String password) {
        User newUser = new User(email, password);
        return userRepository.save(newUser);
    }

    @Transactional(readOnly = true)
    public User findUser(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public User findUserByUuid(String uuid) {
        return userRepository.findByUuid(uuid);
    }

    public boolean deleteUser(String uuid) {
        if (userRepository.exists(uuid)) {
            userRepository.delete(uuid);
            return true;
        }
        return false;
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
