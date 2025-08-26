package com.subcodes.journalApp.service;

import com.subcodes.journalApp.model.User;
import com.subcodes.journalApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder encoder = new BCryptPasswordEncoder();

//    private static final Logger logger = LoggerFactory.getLogger(UserService.class);/

    public void saveUser(User user) {
        if (getUserByUserName(user.getUserName()) != null) {
            userRepository.save(user);
        }
        else {
            log.warn("User with userName '{}' not found, creating new user", user.getUserName());
            saveNewUser(user);
        }
    }

    public void saveNewUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        userRepository.save(user);
    }

    public void saveNewAdmin(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(ObjectId id) {
        return userRepository.findById(id);
    }

    public User getUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public void deleteUserById(ObjectId id) {
        userRepository.deleteById(id);
    }

    public void deleteUser(String username) {
        userRepository.deleteByUserName(username);
    }
}
