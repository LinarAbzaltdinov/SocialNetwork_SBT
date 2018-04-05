package ru.sberbank.socialnetwork.users.services;

import org.springframework.beans.factory.annotation.Autowired;
import ru.sberbank.socialnetwork.users.dao.UserRepository;
import ru.sberbank.socialnetwork.users.entities.User;

import javax.persistence.EntityManager;

public class UserRegitrationService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManager em;

    public User createUser(String email) {
        User newUser = new User(email);
        em.persist(newUser);
        em.flush();
        return newUser;
    }
}
