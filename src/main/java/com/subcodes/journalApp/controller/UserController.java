package com.subcodes.journalApp.controller;

import com.subcodes.journalApp.apiresponse.WeatherResponse;
import com.subcodes.journalApp.model.User;
import com.subcodes.journalApp.service.UserService;
import com.subcodes.journalApp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private WeatherService weatherService;
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
            userFromDb.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.saveUser(userFromDb);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userService.deleteUser(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<?> greetings() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse = weatherService.getWeather("mumbai");
        String greeting = "";
        if (weatherResponse != null) {
            greeting = " Weather feels like "+weatherResponse.getCurrent().getFeelslike();
        }
        return new ResponseEntity<>("Hey there "+authentication.getName()+greeting,HttpStatus.OK);
    }
}
