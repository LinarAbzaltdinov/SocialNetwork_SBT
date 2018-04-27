package ru.sberbank.socialnetwork.users.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sberbank.socialnetwork.users.dao.UserRepository;
import ru.sberbank.socialnetwork.users.entities.User;
import ru.sberbank.socialnetwork.users.services.UserService;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User addUser(String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        User newUser = new User(email, encodedPassword);
        return userRepository.saveAndFlush(newUser);
    }

    @Override
    public User findUserByUuid(String uuid) {
        return userRepository.findByUuid(uuid);
    }

    @Override
    public boolean deleteUser(String uuid) {
        if (userRepository.exists(uuid)) {
            userRepository.delete(uuid);
            return true;
        }
        return false;
    }

    @Override
    public User editUser(User updatedUser) {
        User foundUser = findUserByEmail(updatedUser.getEmail());
        if (foundUser == null) {
            return null;
        }
        foundUser.update(updatedUser);
        return userRepository.saveAndFlush(foundUser);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean login(String email, String password) {
        boolean result;
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return false;
        }
        String foundUserPassword = user.getPassword();
        result = passwordEncoder.matches(password, foundUserPassword);
        return result;
    }
}