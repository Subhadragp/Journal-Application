package com.subcodes.journalApp.service;

import com.subcodes.journalApp.model.User;
import com.subcodes.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveUser(User user) {
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
}
