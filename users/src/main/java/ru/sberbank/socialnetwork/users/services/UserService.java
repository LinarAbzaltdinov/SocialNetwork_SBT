package ru.sberbank.socialnetwork.users.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sberbank.socialnetwork.users.dao.UserRepository;
import ru.sberbank.socialnetwork.users.entities.User;

import javax.persistence.EntityManager;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager em;

    public User createUser(String email) {
        User newUser = new User(email);
        User persistedUser = userRepository.save(newUser);
        return persistedUser;
    }
    @Transactional(readOnly = true)
    public User findUser(String email) {
        User foundUser = userRepository.findByEmail(email);
        return foundUser;
    }
}
