package com.subcodes.journalApp.controller;

import com.subcodes.journalApp.model.User;
import com.subcodes.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
//
//    @GetMapping
//    public List<User> getAllUsers() {
//        return userService.getAllUsers();
//    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User userFromDb = userService.getUserByUserName(username);
        if (userFromDb != null) {
            userFromDb.setUserName(user.getUserName());
            userFromDb.setPassword(user.getPassword());
            userService.saveUser(userFromDb);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
